package ru.jsft.voteforlunch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jsft.voteforlunch.error.NotFoundException;
import ru.jsft.voteforlunch.model.Meal;
import ru.jsft.voteforlunch.repository.MealRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal findById(long id) {
        log.info("Find meal with id = {}", id);
        return repository.findById(id)
                .orElseThrow(() -> (new NotFoundException(String.format("Meal with id = %d not found", id))));
    }

    public List<Meal> findAllSorted() {
        log.info("Find all meals");
        return repository.findAll(Sort.by("name"));
    }

    public Meal create(Meal meal) {
        if (!meal.isNew()) {
            throw new IllegalArgumentException("Meal must be new");
        }

        log.info("Create meal: {}", meal);
        return repository.save(meal);
    }

    public void delete(long id) {
        log.info("Delete meal with id = {}", id);
        repository.deleteById(id);
    }

    @Transactional
    public Meal update(long id, Meal meal) {
        Optional<Meal> mealOptional = repository.findById(id);

        if (mealOptional.isEmpty()) {
            throw new NotFoundException(String.format("Meal with id = %d not found", id));
        }

        log.info("Update meal with id = {}", meal.getId());
        meal.setId(id);
        return repository.save(meal);
    }
}
