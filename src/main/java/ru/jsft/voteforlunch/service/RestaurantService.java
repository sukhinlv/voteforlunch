package ru.jsft.voteforlunch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.jsft.voteforlunch.model.Restaurant;
import ru.jsft.voteforlunch.repository.RestaurantRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Optional<Restaurant> get(long id) {
        log.info("Get restaurant with id={}", id);
        return repository.findById(id);
    }

    public List<Restaurant> getAll() {
        log.info("Get all restaurants");
        return repository.findAll();
    }

    public Restaurant create(String name) {
        log.info("Create restaurant with name={}", name);
        return repository.save(new Restaurant(name));
    }

    public Restaurant update(@NotNull Restaurant restaurant) {
        log.info("Update restaurant with id={}", restaurant.getId());
        return repository.save(restaurant);
    }

    public void delete(long id) {
        log.info("Delete restaurant with id={}", id);
        repository.deleteById(id);
    }
}
