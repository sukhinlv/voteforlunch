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
import ru.jsft.voteforlunch.model.User;
import ru.jsft.voteforlunch.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

class UserServiceTest {
    private UserService underTest;

    @Mock
    private UserRepository repository;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @Captor
    ArgumentCaptor<Long> idCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new UserService(repository);
    }

    @Nested
    class FindUsers {
        @Test
        void shouldFind() {
            User expectedUser = Instancio.create(User.class);
            when(repository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));

            User actualUser = underTest.findById(expectedUser.getId());

            assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser);
        }

        @Test
        void shouldThrowWhenFindNotExisted() {
            when(repository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> underTest.findById(1L))
                    .isInstanceOf(IllegalRequestDataException.class)
                    .hasMessageContaining(String.format("User with id = %d not found", 1L));
        }

        @Test
        void findAll() {
            User user1 = Instancio.create(User.class);
            user1.setEmail("Yuri");
            User user2 = Instancio.create(User.class);
            user2.setEmail("Alex");
            when(repository.findAll(Sort.by("email"))).thenReturn(List.of(user2, user1));

            assertThat(underTest.findAllSorted()).usingRecursiveComparison().isEqualTo(List.of(user2, user1));
        }
    }

    @Nested
    class CreateUser {
        @Test
        void create() {
            User user = Instancio.create(User.class);
            user.setId(null);

            underTest.create(user);
            then(repository).should().save(userCaptor.capture());

            assertThat(userCaptor.getValue())
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(user);
        }
    }

    @Nested
    class DeleteUser {
        @Test
        void delete() {
            underTest.delete(1L);
            then(repository).should().deleteById(idCaptor.capture());

            assertThat(idCaptor.getValue()).isEqualTo(1L);
        }
    }

    @Nested
    class UpdateUser {
        @Test
        void update() {
            User user = Instancio.create(User.class);
            when(repository.findById(user.getId())).thenReturn(Optional.of(user));

            User updatedUser = Instancio.create(User.class);
            underTest.update(user.getId(), updatedUser);
            then(repository).should().save(userCaptor.capture());

            updatedUser.setId(user.getId());
            assertThat(userCaptor.getValue()).usingRecursiveComparison().isEqualTo(updatedUser);
        }

        @Test
        void throwWhenUpdateWrongId() {
            when(repository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> underTest.update(1L, Instancio.create(User.class)))
                    .isInstanceOf(IllegalRequestDataException.class)
                    .hasMessageContaining(String.format("User with id = %d not found", 1L));
        }
    }
}
