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
    @Query("UPDATE Meal m SET m.name = :name  WHERE m.id = :id")
    void updateById(Long id, String name);
}
