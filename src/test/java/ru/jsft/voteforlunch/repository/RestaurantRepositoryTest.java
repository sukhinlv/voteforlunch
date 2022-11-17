package ru.jsft.voteforlunch.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.jsft.voteforlunch.AbstractSpringBootTest;


@Disabled
class RestaurantRepositoryTest extends AbstractSpringBootTest {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    MealPriceRepository mealPriceRepository;

    @Test
    void shouldShouldDeleteAllNestedMenus() {
        // Given
        restaurantRepository.findAll().stream().findFirst().ifPresent(restaurant -> {
            Long id = restaurant.getId();
            restaurantRepository.deleteById(id);
        });


        // When

        // Then
    }
}