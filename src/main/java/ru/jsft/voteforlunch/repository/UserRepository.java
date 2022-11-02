package ru.jsft.voteforlunch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.jsft.voteforlunch.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
