package ru.jsft.voteforlunch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.jsft.voteforlunch.model.MealPrice;

@Repository
public interface MealPriceRepository extends JpaRepository<MealPrice, Long> {
}
