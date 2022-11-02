package ru.jsft.voteforlunch.controller;

import org.springframework.http.HttpStatus;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Meal createNewMeal(@RequestBody Meal meal) {
        return repository.save(meal);
    }

    @DeleteMapping(path = "/{mealId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeal(@PathVariable Long mealId) {
        repository.deleteById(mealId);
    }

    @PutMapping(path = "/{mealId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMeal(@PathVariable Long mealId, @RequestParam String name) {
        repository.updateById(mealId, name);
    }
}
