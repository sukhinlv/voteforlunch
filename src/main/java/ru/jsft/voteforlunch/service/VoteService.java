package ru.jsft.voteforlunch.service;

import org.springframework.stereotype.Service;
import ru.jsft.voteforlunch.model.Vote;
import ru.jsft.voteforlunch.repository.VoteRepository;

import java.util.List;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public List<Vote> findAllVotes() {
        return voteRepository.findAllVotes();
    }
}
