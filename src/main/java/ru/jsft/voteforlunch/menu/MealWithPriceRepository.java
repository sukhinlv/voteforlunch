package ru.jsft.voteforlunch.menu;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MealWithPriceRepository extends JpaRepository<MealWithPrice, Long> {
}