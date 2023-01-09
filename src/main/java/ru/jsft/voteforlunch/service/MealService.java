package ru.jsft.voteforlunch.service;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jsft.voteforlunch.model.Meal;
import ru.jsft.voteforlunch.repository.MealRepository;

import java.util.List;

import static ru.jsft.voteforlunch.validation.ValidationUtils.*;

@Service
@Slf4j
public class MealService {
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal findById(long id) {
        log.info("Find meal with id = {}", id);
        return checkEntityWasFound(repository.findById(id), id, Meal.class);
    }

    public List<Meal> findAllSorted() {
        log.info("Find all meals");
        return repository.findAll(Sort.by("name"));
    }

    @CacheEvict(cacheNames = {"menu", "menus"}, allEntries = true)
    public Meal create(Meal meal) {
        log.info("Create meal: {}", meal);
        checkNew(meal);
        return repository.save(meal);
    }

    @CacheEvict(cacheNames = {"menu", "menus"}, allEntries = true)
    public void delete(long id) {
        log.info("Delete meal with id = {}", id);
        repository.deleteById(id);
    }

    @Transactional
    @CacheEvict(cacheNames = {"menu", "menus"}, allEntries = true)
    public Meal update(long id, @NotNull Meal meal) {
        log.info("Update meal with id = {}", id);
        checkEntityExist(repository.existsById(id), id, Meal.class);
        meal.setId(id);
        return repository.save(meal);
    }
}
