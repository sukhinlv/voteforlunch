package ru.jsft.voteforlunch.service;

import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.jsft.voteforlunch.error.NotFoundException;
import ru.jsft.voteforlunch.model.Menu;
import ru.jsft.voteforlunch.repository.MenuRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

class MenuServiceTest {

    private MenuService underTest;

    @Mock
    private MenuRepository repository;

    @Captor
    ArgumentCaptor<Menu> MenuCaptor;

    @Captor
    ArgumentCaptor<Long> idCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new MenuService(repository);
    }

    @Nested
    class GetMenus {
        @Test
        void shouldGet() {
            Menu menu = Instancio.create(Menu.class);
            when(repository.findById(1L)).thenReturn(Optional.of(menu));

            assertThat(underTest.get(1L)).isNotNull().usingRecursiveComparison().isEqualTo(menu);
        }

        @Test
        void shouldThrowWhenGetNotExisted() {
            when(repository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> underTest.get(1L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessageContaining(String.format("Menu with id = %d not found", 1L));
        }

        @Test
        void getByDate() {
            Menu menu1 = Instancio.create(Menu.class);
            Menu menu2 = Instancio.create(Menu.class);
            Menu menu3 = Instancio.create(Menu.class);
            LocalDate dateOfMenu = LocalDate.of(2022, 10, 10);
            menu1.setDateOfMenu(dateOfMenu);
            menu2.setDateOfMenu(dateOfMenu.plusDays(1));
            menu3.setDateOfMenu(dateOfMenu);
            List<Menu> menuList = List.of(menu1, menu3);
            when(repository.findAllByDateOfMenuOrderByDateOfMenuDesc(dateOfMenu)).thenReturn(menuList);

            assertThat(underTest.getByDate(dateOfMenu))
                    .isNotNull()
                    .usingRecursiveComparison()
                    .isEqualTo(menuList);
        }

        @Test
        void getAll() {
            Menu menu1 = Instancio.create(Menu.class);
            Menu menu2 = Instancio.create(Menu.class);
            Menu menu3 = Instancio.create(Menu.class);
            List<Menu> menuList = List.of(menu1, menu2, menu3);
            when(repository.findAll()).thenReturn(menuList);

            assertThat(underTest.getAll())
                    .isNotNull()
                    .usingRecursiveComparison()
                    .isEqualTo(menuList);
        }
    }

    @Nested
    class CreateMenu {
        @Test
        void shouldCreate() {
            Menu Menu = Instancio.create(Menu.class);
            Menu.setId(null);

            underTest.create(Menu);
            then(repository).should().save(MenuCaptor.capture());

            assertThat(MenuCaptor.getValue())
                    .isNotNull()
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(Menu);
        }

        @Test
        void shouldThrowWhenCreateNotNew() {
            Menu Menu = Instancio.create(Menu.class);

            assertThatThrownBy(() -> underTest.create(Menu))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Menu must be new");
        }
    }

    @Nested
    class DeleteMenu {
        @Test
        void shouldDelete() {
            underTest.delete(1L);
            then(repository).should().deleteById(idCaptor.capture());

            assertThat(idCaptor.getValue()).isNotNull().isEqualTo(1L);
        }
    }

    @Nested
    class UpdateMenu {
        @Test
        void shouldUpdate() {
            Menu Menu = Instancio.create(Menu.class);
            when(repository.findById(Menu.getId())).thenReturn(Optional.of(Menu));

            Menu updatedMenu = Instancio.create(Menu.class);
            underTest.update(Menu.getId(), updatedMenu);
            then(repository).should().save(MenuCaptor.capture());

            updatedMenu.setId(Menu.getId());
            assertThat(MenuCaptor.getValue()).isNotNull().usingRecursiveComparison().isEqualTo(updatedMenu);
        }

        @Test
        void shouldThrowWhenUpdateWrongId() {
            when(repository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> underTest.update(1L, new Menu()))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessageContaining(String.format("Menu with id = %d not found", 1L));
        }
    }
}
