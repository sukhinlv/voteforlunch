package ru.jsft.voteforlunch.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import ru.jsft.voteforlunch.AbstractSpringBootTest;
import ru.jsft.voteforlunch.error.NotFoundException;
import ru.jsft.voteforlunch.model.Meal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SuppressWarnings("ConstantConditions")
@Slf4j
@Transactional
class MealServiceTest extends AbstractSpringBootTest {

    @Autowired
    private MealService serviceUnderTest;

    @Test
    void shouldGet() {
        Meal restaurant = new Meal("dummy");

        Meal getMeal = serviceUnderTest.get(serviceUnderTest.create(restaurant).getId());

        assertThat(getMeal)
                .isNotNull()
                .usingRecursiveComparison().ignoringFields("id").isEqualTo(restaurant);
    }

    @Test
    void shouldThrowWhenGetNotExisted() {
        Long id = serviceUnderTest.create(new Meal("dummy")).getId();

        serviceUnderTest.delete(id);

        assertThatThrownBy(() -> serviceUnderTest.get(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format("Meal with id=%d not found", id));
    }

    @Test
    void shouldCreate() {
        Meal restaurant = new Meal("dummy");

        Meal created = serviceUnderTest.create(restaurant);

        assertThat(created)
                .isNotNull()
                .usingRecursiveComparison().ignoringFields("id").isEqualTo(restaurant);
    }

    @Test
    void shouldThrowWhenCreateExisted() {
        Meal restaurant = serviceUnderTest.create(new Meal("dummy"));

        assertThatThrownBy(() -> serviceUnderTest.create(restaurant))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Meal must be new");
    }

    @Test
    void shouldDelete() {
        Long id = serviceUnderTest.create(new Meal("dummy")).getId();
        serviceUnderTest.delete(id);
    }

    @Test
    void shouldThrowWhenDeleteNotExisted() {
        Long id = serviceUnderTest.create(new Meal("dummy")).getId();
        serviceUnderTest.delete(id);

        assertThatThrownBy(() -> serviceUnderTest.delete(id))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }


    @Test
    void shouldUpdate() {
        Meal restaurant = serviceUnderTest.create(new Meal("dummy"));

        restaurant.setName("updated");
        serviceUnderTest.update(restaurant.getId(), restaurant);
        Meal updated = serviceUnderTest.get(restaurant.getId());

        assertThat(updated).isNotNull().usingRecursiveComparison().isEqualTo(restaurant);
    }

    @Test
    void shouldThrowWhenUpdateWrongId() {
        Meal restaurant = serviceUnderTest.create(new Meal("dummy"));
        Long id = restaurant.getId();
        serviceUnderTest.delete(id);

        assertThatThrownBy(() -> serviceUnderTest.update(id, restaurant))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format("Meal with id=%d not found", id));
    }
}
