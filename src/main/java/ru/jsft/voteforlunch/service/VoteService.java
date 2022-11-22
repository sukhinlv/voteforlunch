package ru.jsft.voteforlunch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jsft.voteforlunch.error.NotFoundException;
import ru.jsft.voteforlunch.error.VoteTimeConstraintException;
import ru.jsft.voteforlunch.model.Vote;
import ru.jsft.voteforlunch.repository.RestaurantRepository;
import ru.jsft.voteforlunch.repository.UserRepository;
import ru.jsft.voteforlunch.repository.VoteRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
public class VoteService {

    private final VoteRepository repository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final Clock clock;
    private final LocalTime timeConstraint;

    public VoteService(VoteRepository repository,
                       UserRepository userRepository,
                       RestaurantRepository restaurantRepository,
                       Clock clock,
                       @Value("${vote.time.constraint}") LocalTime timeConstraint) {

        this.repository = repository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.clock = clock;
        this.timeConstraint = timeConstraint;
    }

    public List<Vote>findAll() {
        log.info("Find all votes");
        return repository.findAll();
    }

    public Vote find(long id, long userId) {
        log.info("Find vote with id = {}, userId = {}", id, userId);
        return repository.findByIdAndUserId(id, userId);
    }

    public List<Vote> findAllForUser(long userId) {
        log.info("Find all votes for userId = {}", userId);
        return repository.findAllForUser(userId);
    }

    @Transactional
    public Vote save(long restaurantId, long userId) {
        log.info("Try to save vote. RestaurantID = {}, UserId = {}", restaurantId, userId);
        if (LocalTime.now(clock).isAfter(timeConstraint)) {
            throw new VoteTimeConstraintException(String.format("You can only change your vote until %s", timeConstraint));
        }

        Vote vote = repository.findByVoteDateAndUserId(LocalDate.now(clock), userId);
        if (vote == null) {
            vote = new Vote();
            vote.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
            vote.setUser(userRepository.getReferenceById(userId));
            vote.setVoteDate(LocalDate.now(clock));
            vote.setVoteTime(LocalTime.now(clock));
            vote = repository.save(vote);
            log.info("Vote saved. RestaurantID = {}, UserId = {}", restaurantId, userId);
        } else {
            vote.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
            vote.setVoteTime(LocalTime.now(clock));
            vote = repository.save(vote);
            log.info("Vote updated. RestaurantID = {}, UserId = {}", restaurantId, userId);
        }
        return vote;
    }

    @Transactional
    public void delete(long userId) {
        log.info("Try to delete vote of userId={}", userId);
        if (LocalTime.now(clock).isAfter(timeConstraint)) {
            throw new VoteTimeConstraintException(String.format("You can only change your vote until %s", timeConstraint));
        }
        Vote vote = repository.findByVoteDateAndUserId(LocalDate.now(clock), userId);
        if (vote == null) {
            throw new NotFoundException(String.format("Vote of userId = %s for date = %s not found", userId, LocalDate.now(clock)));
        }
        repository.deleteById(vote.getId());
        log.info("Vote deleted. userId={}", userId);
    }
}
