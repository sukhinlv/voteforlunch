package ru.jsft.voteforlunch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.jsft.voteforlunch.error.NotFoundException;
import ru.jsft.voteforlunch.model.Vote;
import ru.jsft.voteforlunch.repository.VoteRepository;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Validated
public class VoteService {

    private final VoteRepository repository;

    @Value("${vote.time.constraint}")
    LocalTime timeConstraint;

    public VoteService(VoteRepository repository) {
        this.repository = repository;
    }

    public Vote get(long id) {
        log.info("Get vote with id={}", id);
        return repository.findById(id)
                .orElseThrow(() -> (new NotFoundException(String.format("Vote with id=%d not found", id))));
    }

    public List<Vote> getAll() {
        log.info("Get all votes");
        return repository.findAll(Sort.by("name"));
    }

    public Vote create(@NotNull Vote vote) {
        if (!vote.isNew()) {
            throw new IllegalArgumentException("Vote must be new");
        }

        log.info("Create vote: {}", vote);
        return repository.save(vote);
    }

    public void delete(long id) {
        log.info("Delete vote with id={}", id);
        repository.deleteById(id);
    }

    public Vote update(Long id, @NotNull Vote vote) {
        Optional<Vote> voteOptional = repository.findById(id);

        if (voteOptional.isEmpty()) {
            throw new NotFoundException(String.format("Vote with id=%d not found", id));
        }

        log.info("Update vote with id={}", vote.getId());
        vote.setId(id);
        return repository.save(vote);
    }
}
