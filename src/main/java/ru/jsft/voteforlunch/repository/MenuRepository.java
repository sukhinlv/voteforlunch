package ru.jsft.voteforlunch.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jsft.voteforlunch.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @EntityGraph(attributePaths = "restaurant")
    List<Menu> findAllByDateOfMenuOrderByDateOfMenuDesc(LocalDate date);

    @EntityGraph(attributePaths = "restaurant")
    @Query("select m from Menu m order by m.dateOfMenu desc")
    List<Menu> findAllWithRestaurants();

    @EntityGraph(attributePaths = {"restaurant", "mealPrice", "mealPrice.meal"})
    @Query("select m from Menu m where m.id = :id")
    Optional<Menu> findByIdWithProps(@Param("id") long id);
}
