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
import ru.jsft.voteforlunch.error.IllegalRequestDataException;
import ru.jsft.voteforlunch.model.Dish;
import ru.jsft.voteforlunch.repository.DishRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

class DishServiceTest {
    private DishService underTest;

    @Mock
    private DishRepository repository;

    @Captor
    ArgumentCaptor<Dish> dishCaptor;

    @Captor
    ArgumentCaptor<Long> idCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new DishService(repository);
    }

    @Nested
    class FindDishes {
        @Test
        void find() {
            Dish expectedDish = Instancio.create(Dish.class);
            when(repository.findById(expectedDish.getId())).thenReturn(Optional.of(expectedDish));

            Dish actualDish = underTest.findById(expectedDish.getId());

            assertThat(actualDish).usingRecursiveComparison().isEqualTo(expectedDish);
        }

        @Test
        void throwWhenFindNotExisted() {
            when(repository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> underTest.findById(1L))
                    .isInstanceOf(IllegalRequestDataException.class)
                    .hasMessageContaining(String.format("Dish with id = %d not found", 1L));
        }

        @Test
        void findAll() {
            Dish dish1 = Instancio.create(Dish.class);
            dish1.setName("Zebra pie");
            Dish dish2 = Instancio.create(Dish.class);
            dish2.setName("Apple pie");
            when(repository.findAll(Sort.by("name"))).thenReturn(List.of(dish2, dish1));

            assertThat(underTest.findAllSorted()).usingRecursiveComparison().isEqualTo(List.of(dish2, dish1));
        }
    }

    @Nested
    class CreateDish {
        @Test
        void create() {
            Dish dish = Instancio.create(Dish.class);
            dish.setId(null);

            underTest.create(dish);
            then(repository).should().save(dishCaptor.capture());

            assertThat(dishCaptor.getValue())
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(dish);
        }
    }

    @Nested
    class DeleteDish {
        @Test
        void delete() {
            underTest.delete(1L);
            then(repository).should().deleteById(idCaptor.capture());

            assertThat(idCaptor.getValue()).isEqualTo(1L);
        }
    }

    @Nested
    class UpdateDish {
        @Test
        void update() {
            Dish dish = Instancio.create(Dish.class);
            when(repository.existsById(dish.getId())).thenReturn(true);

            Dish updatedDish = Instancio.create(Dish.class);
            underTest.update(dish.getId(), updatedDish);
            then(repository).should().save(dishCaptor.capture());

            updatedDish.setId(dish.getId());
            assertThat(dishCaptor.getValue()).usingRecursiveComparison().isEqualTo(updatedDish);
        }

        @Test
        void throwWhenUpdateWrongId() {
            when(repository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> underTest.update(1L, new Dish()))
                    .isInstanceOf(IllegalRequestDataException.class)
                    .hasMessageContaining(String.format("Dish with id = %d not found", 1L));
        }
    }
}
