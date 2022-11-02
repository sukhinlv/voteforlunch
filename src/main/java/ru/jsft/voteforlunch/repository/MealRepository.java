package ru.jsft.voteforlunch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jsft.voteforlunch.model.Meal;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    @Modifying
    @Transactional
    @Query("update Meal m set m.name = :name where m.id = :id")
    void updateById(Long id, String name);

    @Query("select m from Meal m where m.id = :id")
    Meal findMealById(Long id);
}
