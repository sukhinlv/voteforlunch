package ru.jsft.voteforlunch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jsft.voteforlunch.model.Vote;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("select v from Vote v left join fetch v.restaurant left join fetch v.user")
    List<Vote> findAllVotes();

}
