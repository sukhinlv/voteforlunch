package ru.jsft.voteforlunch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.jsft.voteforlunch.error.NotFoundException;
import ru.jsft.voteforlunch.model.Menu;
import ru.jsft.voteforlunch.repository.MenuRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MenuService {

    private final MenuRepository repository;

    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }

    public Menu get(long id) {
        log.info("Get menu with id={}", id);
        return repository.findById(id)
                .orElseThrow(() -> (new NotFoundException(String.format("Menu with id=%d not found", id))));
    }

    public List<Menu> getByDate(LocalDate date) {
        return repository.findAllByDateOfMenuOrderByDateOfMenuDesc(date);
    }

    public List<Menu> getAll() {
        log.info("Get all menus");
        return repository.findAll();
    }

    public Menu create(Menu menu) {
        if (!menu.isNew()) {
            throw new IllegalArgumentException("Menu must be new");
        }

        log.info("Create menu: {}", menu);
        return repository.save(menu);
    }

    public void delete(long id) {
        log.info("Delete menu with id={}", id);
        repository.deleteById(id);
    }

    public Menu update(long id, Menu menu) {
        Optional<Menu> menuOptional = repository.findById(id);

        if (menuOptional.isEmpty()) {
            throw new NotFoundException(String.format("Menu with id=%d not found", id));
        }

        log.info("Update menu with id={}", menu.getId());
        menu.setId(id);
        return repository.save(menu);
    }
}
