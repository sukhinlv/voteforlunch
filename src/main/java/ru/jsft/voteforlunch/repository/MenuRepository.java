package ru.jsft.voteforlunch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.jsft.voteforlunch.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
