package ru.jsft.voteforlunch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jsft.voteforlunch.model.Menu;
import ru.jsft.voteforlunch.repository.MenuRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static ru.jsft.voteforlunch.validation.ValidationUtils.checkEntityNotNull;
import static ru.jsft.voteforlunch.validation.ValidationUtils.checkNew;

@Service
@Slf4j
public class MenuService {

    private final MenuRepository repository;

    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }

    @Cacheable("menus")
    public List<Menu> findAllByDateWithProps(LocalDate date) {
        log.info("Find all menus (including properties) on date = {}", date);
        return repository.findAllByDateOfMenuOrderByDateOfMenuDesc(date);
    }

    @CacheEvict({"menu"})
    public Menu create(Menu menu) {
        log.info("Create menu: {}", menu);
        checkNew(menu);
        return repository.save(menu);
    }

    @Cacheable("menus")
    public List<Menu> findAll() {
        log.info("Find all menus");
        return repository.findAll();
    }

    @Cacheable("menus")
    public List<Menu> findAllWithRestaurants() {
        log.info("Find all menus with restaurants");
        return repository.findAllWithRestaurants();
    }

    @Transactional
    @CacheEvict({"menu", "menus"})
    public Menu updateAndReturnWithDetails(long id, Menu menu) {
        Menu updatedMenu = update(id, menu);
        return findByIdWithProps(Objects.requireNonNull(updatedMenu.getId()));
    }

    @Transactional
    @CacheEvict({"menu", "menus"})
    public Menu update(long id, Menu menu) {
        log.info("Update menu with id = {}", menu.getId());
        checkEntityNotNull(repository.findById(id), id, Menu.class);
        menu.setId(id);
        return repository.save(menu);
    }

    @CacheEvict({"menu", "menus"})
    public void delete(long id) {
        log.info("Delete menu with id = {}", id);
        repository.deleteById(id);
    }

    @Cacheable("menu")
    public Menu findByIdWithProps(long id) {
        log.info("Find menu (including properties) with id = {}", id);
        return checkEntityNotNull(repository.findByIdWithProps(id), id, Menu.class);
    }
}
