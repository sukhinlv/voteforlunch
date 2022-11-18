package ru.jsft.voteforlunch.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jsft.voteforlunch.model.Menu;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @EntityGraph(attributePaths = "restaurant")
    List<Menu> findAllByDateOfMenuOrderByDateOfMenuDesc(LocalDate date);
}
