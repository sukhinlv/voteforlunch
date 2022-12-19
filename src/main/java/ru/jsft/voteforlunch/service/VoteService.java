package ru.jsft.voteforlunch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.jsft.voteforlunch.error.IllegalRequestDataException;
import ru.jsft.voteforlunch.error.VoteTimeConstraintException;
import ru.jsft.voteforlunch.model.Vote;
import ru.jsft.voteforlunch.model.VoteDistribution;
import ru.jsft.voteforlunch.repository.RestaurantRepository;
import ru.jsft.voteforlunch.repository.UserRepository;
import ru.jsft.voteforlunch.repository.VoteRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.jsft.voteforlunch.validation.ValidationUtils.checkEntityNotNull;

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
        return checkEntityNotNull(repository.findByIdAndUserId(id, userId), id, Vote.class);
    }

    public List<Vote> findAllForUser(long userId) {
        log.info("Find all votes for userId = {}", userId);
        return repository.findAllForUser(userId);
    }

    @Transactional
    @CacheEvict("voteDistribution")
    public Vote save(long restaurantId, long userId) {
        log.info("Try to save vote. RestaurantID = {}, UserId = {}", restaurantId, userId);
        if (LocalTime.now(clock).isAfter(timeConstraint)) {
            throw new VoteTimeConstraintException(String.format("You can only vote until %s", timeConstraint));
        }

        Vote vote = repository.findByVoteDateAndUserId(LocalDate.now(clock), userId).orElseGet(() -> {
            Vote newVote = new Vote();
            newVote.setUser(userRepository.getReferenceById(userId));
            newVote.setVoteDate(LocalDate.now(clock));
            return newVote;
        });
        vote.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        vote.setVoteTime(LocalTime.now(clock));
        vote = repository.save(vote);
        log.info("Vote saved. RestaurantID = {}, UserId = {}", restaurantId, userId);
        return vote;
    }

    @Transactional
    @CacheEvict("voteDistribution")
    public Vote saveAndReturnWithDetails(long restaurantId, long userId) {
        Vote savedVote = save(restaurantId, userId);
        return find(savedVote.getId(), savedVote.getUser().getId());
    }

    @Transactional
    @CacheEvict("voteDistribution")
    public void delete(long userId) {
        log.info("Try to delete vote of userId={}", userId);
        if (LocalTime.now(clock).isAfter(timeConstraint)) {
            throw new VoteTimeConstraintException(String.format("You can only change your vote until %s", timeConstraint));
        }

        Vote vote = repository.findByVoteDateAndUserId(LocalDate.now(clock), userId)
                .orElseThrow(() -> new IllegalRequestDataException(String.format("Vote of userId = %s for date = %s not found", userId, LocalDate.now(clock))));

        repository.deleteById(vote.getId());
        log.info("Vote deleted. userId={}", userId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Cacheable("voteDistribution")
    public List<VoteDistribution> getVotesDistributionOnDate(LocalDate date) {
        log.info("Get votes distribution on {}", date);
        return repository.getVotesDistributionOnDate(date);
    }
}
