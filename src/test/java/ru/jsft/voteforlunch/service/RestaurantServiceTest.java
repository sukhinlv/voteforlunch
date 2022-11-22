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
import ru.jsft.voteforlunch.model.Restaurant;
import ru.jsft.voteforlunch.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

class RestaurantServiceTest {

    private RestaurantService underTest;

    @Mock
    private RestaurantRepository repository;

    @Captor
    ArgumentCaptor<Restaurant> restaurantCaptor;

    @Captor
    ArgumentCaptor<Long> idCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new RestaurantService(repository);
    }

    @Nested
    class FindRestaurants {
        @Test
        void shouldFind() {
            Restaurant expectedRestaurant = Instancio.create(Restaurant.class);
            when(repository.findById(expectedRestaurant.getId())).thenReturn(Optional.of(expectedRestaurant));

            Restaurant actualRestaurant = underTest.findById(expectedRestaurant.getId());

            assertThat(actualRestaurant)
                    .isNotNull()
                    .usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedRestaurant);
        }

        @Test
        void shouldThrowWhenFindNotExisted() {
            when(repository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> underTest.findById(1L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessageContaining(String.format("Restaurant with id = %d not found", 1L));
        }

        @Test
        void shouldFindAll() {
            Restaurant restaurant1 = Instancio.create(Restaurant.class);
            restaurant1.setName("Zara");
            Restaurant restaurant2 = Instancio.create(Restaurant.class);
            restaurant2.setName("Aisha");
            when(repository.findAll(Sort.by("name"))).thenReturn(List.of(restaurant2, restaurant1));

            assertThat(underTest.findAll()).isNotNull().usingRecursiveComparison().isEqualTo(List.of(restaurant2, restaurant1));
        }
    }

    @Nested
    class CreateRestaurant {
        @Test
        void shouldCreate() {
            Restaurant restaurant = Instancio.create(Restaurant.class);
            restaurant.setId(null);

            underTest.create(restaurant);
            then(repository).should().save(restaurantCaptor.capture());

            assertThat(restaurantCaptor.getValue())
                    .isNotNull()
                    .usingRecursiveComparison().ignoringFields("id").isEqualTo(restaurant);
        }

        @Test
        void shouldThrowWhenCreateNotNew() {
            Restaurant restaurant = Instancio.create(Restaurant.class);

            assertThatThrownBy(() -> underTest.create(restaurant))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Restaurant must be new");
        }
    }

    @Nested
    class DeleteRestaurant {
        @Test
        void shouldDelete() {
            underTest.delete(1L);
            then(repository).should().deleteById(idCaptor.capture());

            assertThat(idCaptor.getValue()).isEqualTo(1L);
        }
    }

    @Nested
    class UpdateRestaurant {
        @Test
        void shouldUpdate() {
            Restaurant restaurant = Instancio.create(Restaurant.class);
            when(repository.findById(restaurant.getId())).thenReturn(Optional.of(restaurant));

            Restaurant updatedRestaurant = Instancio.create(Restaurant.class);
            underTest.update(restaurant.getId(), updatedRestaurant);
            then(repository).should().save(restaurantCaptor.capture());

            updatedRestaurant.setId(restaurant.getId());
            assertThat(restaurantCaptor.getValue()).usingRecursiveComparison().isEqualTo(updatedRestaurant);
        }

        @Test
        void shouldThrowWhenUpdateWrongId() {
            when(repository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> underTest.update(1L, new Restaurant()))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessageContaining(String.format("Restaurant with id = %d not found", 1L));
        }
    }
}
