package ru.jsft.voteforlunch.service;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jsft.voteforlunch.model.Dish;
import ru.jsft.voteforlunch.repository.DishRepository;

import java.util.List;

import static ru.jsft.voteforlunch.validation.ValidationUtils.checkEntityExist;
import static ru.jsft.voteforlunch.validation.ValidationUtils.checkEntityWasFound;

@Service
@Slf4j
public class DishService {
    private final DishRepository repository;

    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    public Dish findById(long id) {
        log.info("Find dish with id = {}", id);
        return checkEntityWasFound(repository.findById(id), id, Dish.class);
    }

    public List<Dish> findAllSorted() {
        log.info("Find all dishes");
        return repository.findAll(Sort.by("name"));
    }

    @CacheEvict(cacheNames = {"menu", "menus"}, allEntries = true)
    public Dish create(Dish dish) {
        log.info("Create dish: {}", dish);
        return repository.save(dish);
    }

    @CacheEvict(cacheNames = {"menu", "menus"}, allEntries = true)
    public void delete(long id) {
        log.info("Delete dish with id = {}", id);
        repository.deleteById(id);
    }

    @Transactional
    @CacheEvict(cacheNames = {"menu", "menus"}, allEntries = true)
    public Dish update(long id, @NotNull Dish dish) {
        log.info("Update dish with id = {}", id);
        checkEntityExist(repository.existsById(id), id, Dish.class);
        dish.setId(id);
        return repository.save(dish);
    }
}
