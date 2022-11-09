package ru.jsft.voteforlunch.controller.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jsft.voteforlunch.controller.dto.MenuDto;
import ru.jsft.voteforlunch.controller.mapper.impl.MenuMapper;
import ru.jsft.voteforlunch.model.Menu;
import ru.jsft.voteforlunch.service.MenuService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/menus")
public class MenuController {

    private final MenuService service;

    private final MenuMapper mapper;

    public MenuController(MenuService service, MenuMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<Menu> getAll() {
        // TODO results not converted to DTO
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDto> get(@PathVariable long id) {
        return ResponseEntity.ok(mapper.toDto(service.get(id)));
    }

    @PostMapping
    public ResponseEntity<MenuDto> create(@Valid @RequestBody MenuDto menuDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(service.create(mapper.toEntity(menuDto))));
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<MenuDto> update(@PathVariable long id, @Valid @RequestBody MenuDto menuDto) {
        return ResponseEntity.ok(mapper.toDto(service.update(id, mapper.toEntity(menuDto))));
    }
}
