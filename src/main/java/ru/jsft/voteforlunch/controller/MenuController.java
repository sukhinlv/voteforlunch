package ru.jsft.voteforlunch.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jsft.voteforlunch.controller.dto.MenuDto;
import ru.jsft.voteforlunch.controller.dto.MenuListDto;
import ru.jsft.voteforlunch.controller.mapper.MenuListMapper;
import ru.jsft.voteforlunch.controller.mapper.MenuMapper;
import ru.jsft.voteforlunch.model.Menu;
import ru.jsft.voteforlunch.service.MenuService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("api/v1/menus")
@Slf4j
public class MenuController {

    private final MenuService service;

    private final MenuMapper mapper;

    private final MenuListMapper listMapper;

    public MenuController(MenuService service, MenuMapper mapper, MenuListMapper listMapper) {
        this.service = service;
        this.mapper = mapper;
        this.listMapper = listMapper;
    }

    @GetMapping
    public ResponseEntity<List<MenuListDto>> getAll(
            @RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<Menu> resultList = (date == null) ? service.findAllWithRestaurants() : service.findByDateWithProps(date);
        return ResponseEntity.ok(resultList.stream()
                .map(listMapper::toDto)
                .sorted(Comparator.comparing(MenuListDto::getDateOfMenu)
                        .reversed()
                        .thenComparing(MenuListDto::getRestaurantName))
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDto> get(@PathVariable long id) {
        return ResponseEntity.ok(mapper.toDto(service.findByIdWithProps(id)));
    }

    @PostMapping
    public ResponseEntity<MenuDto> create(@Valid @RequestBody MenuDto menuDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(service.create(mapper.toEntity(menuDto))));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<MenuDto> update(@PathVariable long id, @Valid @RequestBody MenuDto menuDto) {
        return ResponseEntity.ok(mapper.toDto(service.update(id, mapper.toEntity(menuDto))));
    }
}
