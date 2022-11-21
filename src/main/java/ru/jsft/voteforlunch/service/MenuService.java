package ru.jsft.voteforlunch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jsft.voteforlunch.error.NotFoundException;
import ru.jsft.voteforlunch.model.Menu;
import ru.jsft.voteforlunch.repository.MenuRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class MenuService {

    private final MenuRepository repository;

    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }

    public Menu findByIdWithProps(long id) {
        log.info("Find menu (include properties) with id = {}", id);
        return repository.findByIdWithProps(id)
                .orElseThrow(() -> (new NotFoundException(String.format("Menu with id = %d not found", id))));
    }

    public List<Menu> findByDateWithProps(LocalDate date) {
        return repository.findAllByDateOfMenuOrderByDateOfMenuDesc(date);
    }

    public List<Menu> findAll() {
        log.info("Find all menus");
        return repository.findAll();
    }

    public List<Menu> findAllWithRestaurants() {
        log.info("Find all menus with restaurants");
        return repository.findAllWithRestaurants();
    }

    public Menu create(Menu menu) {
        if (!menu.isNew()) {
            throw new IllegalArgumentException("Menu must be new");
        }

        log.info("Create menu: {}", menu);
        return repository.save(menu);
    }

    public void delete(long id) {
        log.info("Delete menu with id = {}", id);
        repository.deleteById(id);
    }

    @Transactional
    public Menu update(Menu menu) {
        if (menu.isNew()) {
            throw new IllegalArgumentException("Menu must have id");
        }

        if (!repository.existsById(Objects.requireNonNull(menu.getId()))) {
            throw new NotFoundException(String.format("Menu with id = %d not found", menu.getId()));
        }

        log.info("Update menu with id = {}", menu.getId());
        return repository.save(menu);
    }
}
