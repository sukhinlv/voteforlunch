package ru.jsft.voteforlunch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.jsft.voteforlunch.model.Meal;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
}
