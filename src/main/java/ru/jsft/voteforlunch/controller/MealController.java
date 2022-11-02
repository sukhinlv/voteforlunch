package ru.jsft.voteforlunch.controller;

import org.springframework.web.bind.annotation.*;
import ru.jsft.voteforlunch.model.Meal;
import ru.jsft.voteforlunch.repository.MealRepository;

import java.util.List;

@RestController
@RequestMapping("api/v1/meals")
public class MealController {

    private final MealRepository repository;

    public MealController(MealRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Meal> getMeals() {
        return repository.findAll();
    }

    @GetMapping("/{mealId}")
    public Meal getMealById(@PathVariable Long mealId) {
        return repository.findById(mealId).orElse(null);
    }

    @PostMapping
    public Meal createNewMeal(@RequestBody Meal meal) {
        return repository.save(meal);
    }

    @DeleteMapping(path = "/{mealId}")
    public void deleteMeal(@PathVariable Long mealId) {
        repository.deleteById(mealId);
    }

    @PutMapping(path = "/{mealId}")
    public Meal updateMeal(@PathVariable Long mealId, @RequestParam String name) {
        Meal meal = new Meal();
        meal.setId(mealId);
        return repository.save(meal);
    }
}
