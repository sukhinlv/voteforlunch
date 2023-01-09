package ru.jsft.voteforlunch.service;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jsft.voteforlunch.model.Menu;
import ru.jsft.voteforlunch.repository.MenuRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static ru.jsft.voteforlunch.validation.ValidationUtils.*;

@Service
@Slf4j
public class MenuService {
    private final MenuRepository repository;

    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }

    @Caching(
            put = {@CachePut(key = "#result.id", value = "menu")},
            evict = {@CacheEvict(key = "#result.dateOfMenu", value = "menus")}
    )
    public Menu create(Menu menu) {
        log.info("Create menu: {}", menu);
        checkNew(menu);
        return repository.save(menu);
    }

    @Cacheable(key = "#date", value = "menus")
    public List<Menu> findAllWithRestaurants(@NotNull LocalDate date) {
        log.info("Find all menus with restaurants on {}", date);
        return repository.findAllWithRestaurantsOnDate(date);
    }

    @Transactional
    @Caching(
            put = {@CachePut(key = "#result.id", value = "menu")},
            evict = {@CacheEvict(key = "#result.dateOfMenu", value = "menus")}
    )
    public Menu updateAndReturnWithDetails(long id, Menu menu) {
        log.info("Update menu with id = {}", menu.getId());
        checkEntityExist(repository.existsById(id), id, Menu.class);
        menu.setId(id);
        Menu updatedMenu = repository.save(menu);
        return findByIdWithAllData(Objects.requireNonNull(updatedMenu.getId()));
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "#id", value = "menu"),
                    @CacheEvict(value = "menus", allEntries = true)
            }
    )
    public void delete(long id) {
        log.info("Delete menu with id = {}", id);
        repository.deleteById(id);
    }

    @Cacheable(key = "#id", value = "menu")
    public Menu findByIdWithAllData(long id) {
        log.info("Find menu (including properties) with id = {}", id);
        return checkEntityWasFound(repository.findByIdWithAllData(id), id, Menu.class);
    }
}
