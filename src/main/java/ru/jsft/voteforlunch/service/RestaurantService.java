package ru.jsft.voteforlunch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jsft.voteforlunch.error.NotFoundException;
import ru.jsft.voteforlunch.model.Restaurant;
import ru.jsft.voteforlunch.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant findById(long id) {
        log.info("Find restaurant with id = {}", id);
        return repository.findById(id)
                .orElseThrow(() -> (new NotFoundException(String.format("Restaurant with id = %d not found", id))));
    }

    public List<Restaurant> findAll() {
        log.info("Find all restaurants");
        return repository.findAll(Sort.by("name"));
    }

    public Restaurant create(Restaurant restaurant) {
        if (!restaurant.isNew()) {
            throw new IllegalArgumentException("Restaurant must be new");
        }

        log.info("Create restaurant: {}", restaurant);
        return repository.save(restaurant);
    }

    public void delete(long id) {
        log.info("Delete restaurant with id = {}", id);
        repository.deleteById(id);
    }

    @Transactional
    public Restaurant update(long id, Restaurant restaurant) {
        Optional<Restaurant> restaurantOptional = repository.findById(id);

        if (restaurantOptional.isEmpty()) {
            throw new NotFoundException(String.format("Restaurant with id = %d not found", id));
        }

        log.info("Update restaurant with id = {}", restaurant.getId());
        restaurant.setId(id);
        return repository.save(restaurant);
    }
}
