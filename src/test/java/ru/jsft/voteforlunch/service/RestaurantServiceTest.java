package ru.jsft.voteforlunch.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.jsft.voteforlunch.AbstractSpringBootTest;
import ru.jsft.voteforlunch.model.Restaurant;

@Slf4j
class RestaurantServiceTest extends AbstractSpringBootTest {

    @Autowired
    private RestaurantService service;

    @Test
    void itShouldGet() {
        Restaurant restaurant1 = service.create("Cafee One");
        Restaurant restaurant2 = service.create("Cafee Two");

        log.info("get restaurant: {}", service.get(restaurant1.getId()));
        log.info("get restaurant: {}", service.get(restaurant2.getId()));
        log.info("get restaurant: {}", service.get(restaurant1.getId()));
        log.info("get restaurant: {}", service.get(restaurant2.getId()));
    }
}
