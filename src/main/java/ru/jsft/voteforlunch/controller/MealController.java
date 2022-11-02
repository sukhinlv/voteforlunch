package ru.jsft.voteforlunch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jsft.voteforlunch.model.Meal;
import ru.jsft.voteforlunch.service.MealService;

import java.util.List;

@RestController
@RequestMapping("api/v1/meals")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping
    public List<Meal> getMeals() {
        return mealService.getAllMeals();
    }
}
