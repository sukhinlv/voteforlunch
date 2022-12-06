package ru.jsft.voteforlunch.service;

import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import ru.jsft.voteforlunch.error.NotFoundException;
import ru.jsft.voteforlunch.model.Meal;
import ru.jsft.voteforlunch.repository.MealRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

class MealServiceTest {

    private MealService underTest;

    @Mock
    private MealRepository repository;

    @Captor
    ArgumentCaptor<Meal> mealCaptor;

    @Captor
    ArgumentCaptor<Long> idCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new MealService(repository);
    }

    @Nested
    class FindMeals {
        @Test
        void find() {
            Meal expectedMeal = Instancio.create(Meal.class);
            when(repository.findById(expectedMeal.getId())).thenReturn(Optional.of(expectedMeal));

            Meal actualMeal = underTest.findById(expectedMeal.getId());

            assertThat(actualMeal)
                    .isNotNull()
                    .usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedMeal);
        }

        @Test
        void throw_When_Find_Not_Existed() {
            when(repository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> underTest.findById(1L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessageContaining(String.format("Meal with id = %d not found", 1L));
        }

        @Test
        void find_All() {
            Meal meal1 = Instancio.create(Meal.class);
            meal1.setName("Zara");
            Meal meal2 = Instancio.create(Meal.class);
            meal2.setName("Aisha");
            when(repository.findAll(Sort.by("name"))).thenReturn(List.of(meal2, meal1));

            assertThat(underTest.findAllSorted()).isNotNull().usingRecursiveComparison().isEqualTo(List.of(meal2, meal1));
        }
    }

    @Nested
    class CreateMeal {
        @Test
        void create() {
            Meal meal = Instancio.create(Meal.class);
            meal.setId(null);

            underTest.create(meal);
            then(repository).should().save(mealCaptor.capture());

            assertThat(mealCaptor.getValue())
                    .isNotNull()
                    .usingRecursiveComparison().ignoringFields("id").isEqualTo(meal);
        }

        @Test
        void throw_When_Create_Not_New() {
            Meal meal = Instancio.create(Meal.class);

            assertThatThrownBy(() -> underTest.create(meal))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Meal must be new");
        }
    }

    @Nested
    class DeleteMeal {
        @Test
        void delete() {
            underTest.delete(1L);
            then(repository).should().deleteById(idCaptor.capture());

            assertThat(idCaptor.getValue()).isEqualTo(1L);
        }
    }

    @Nested
    class UpdateMeal {
        @Test
        void update() {
            Meal meal = Instancio.create(Meal.class);
            when(repository.findById(meal.getId())).thenReturn(Optional.of(meal));

            Meal updatedMeal = Instancio.create(Meal.class);
            underTest.update(meal.getId(), updatedMeal);
            then(repository).should().save(mealCaptor.capture());

            updatedMeal.setId(meal.getId());
            assertThat(mealCaptor.getValue()).usingRecursiveComparison().isEqualTo(updatedMeal);
        }

        @Test
        void throw_When_Update_Wrong_Id() {
            when(repository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> underTest.update(1L, new Meal()))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessageContaining(String.format("Meal with id = %d not found", 1L));
        }
    }
}
