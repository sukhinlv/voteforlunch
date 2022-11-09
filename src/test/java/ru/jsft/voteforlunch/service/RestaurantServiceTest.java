package ru.jsft.voteforlunch.service;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.jsft.voteforlunch.AbstractSpringBootTest;
import ru.jsft.voteforlunch.error.NotFoundException;
import ru.jsft.voteforlunch.model.Restaurant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SuppressWarnings("ConstantConditions")
@Slf4j
class RestaurantServiceTest extends AbstractSpringBootTest {

    @Autowired
    private RestaurantService serviceUnderTest;

    private final Faker faker = new Faker();

    @Test
    void shouldGet() {
        Restaurant restaurant = new Restaurant(faker.name().name());

        Restaurant getRestaurant = serviceUnderTest.get(serviceUnderTest.create(restaurant).getId());

        assertThat(getRestaurant)
                .isNotNull()
                .usingRecursiveComparison().ignoringFields("id").isEqualTo(restaurant);
    }

    @Test
    void shouldThrowWhenGetNotExisted() {
        Long id = serviceUnderTest.create(new Restaurant(faker.name().name())).getId();

        serviceUnderTest.delete(id);

        assertThatThrownBy(() -> serviceUnderTest.get(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format("Restaurant with id=%d not found", id));
    }

    @Test
    void shouldCreate() {
        Restaurant restaurant = new Restaurant(faker.name().name());

        Restaurant created = serviceUnderTest.create(restaurant);

        assertThat(created)
                .isNotNull()
                .usingRecursiveComparison().ignoringFields("id").isEqualTo(restaurant);
    }

    @Test
    void shouldThrowWhenCreateExisted() {
        Restaurant restaurant = serviceUnderTest.create(new Restaurant(faker.name().name()));

        assertThatThrownBy(() -> serviceUnderTest.create(restaurant))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Restaurant must be new");
    }

    @Test
    void shouldDelete() {
        Long id = serviceUnderTest.create(new Restaurant(faker.name().name())).getId();
        serviceUnderTest.delete(id);
    }

    @Test
    void shouldThrowWhenDeleteNotExisted() {
        Long id = serviceUnderTest.create(new Restaurant(faker.name().name())).getId();
        serviceUnderTest.delete(id);

        assertThatThrownBy(() -> serviceUnderTest.delete(id))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }


    @Test
    void shouldUpdate() {
        Restaurant restaurant = serviceUnderTest.create(new Restaurant(faker.name().name()));

        restaurant.setName("updated");
        serviceUnderTest.update(restaurant.getId(), restaurant);
        Restaurant updated = serviceUnderTest.get(restaurant.getId());

        assertThat(updated).isNotNull().usingRecursiveComparison().isEqualTo(restaurant);
    }

    @Test
    void shouldThrowWhenUpdateWrongId() {
        Restaurant restaurant = serviceUnderTest.create(new Restaurant(faker.name().name()));
        Long id = restaurant.getId();
        serviceUnderTest.delete(id);

        assertThatThrownBy(() -> serviceUnderTest.update(id, restaurant))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format("Restaurant with id=%d not found", id));
    }
}
