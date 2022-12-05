package ru.jsft.voteforlunch.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.jsft.voteforlunch.controller.dto.MenuDto;
import ru.jsft.voteforlunch.controller.dto.MenuListDto;
import ru.jsft.voteforlunch.controller.mapper.MenuListMapper;
import ru.jsft.voteforlunch.controller.mapper.MenuMapper;
import ru.jsft.voteforlunch.model.Menu;
import ru.jsft.voteforlunch.service.MenuService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {
    public static final String REST_URL = "/api/v1/menus";

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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuDto> create(@Valid @RequestBody MenuDto menuDto) {
        Menu created = service.create(mapper.toEntity(menuDto));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(mapper.toDto(created));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuDto> update(@Valid @RequestBody MenuDto menuDto) {
        return ResponseEntity.ok(mapper.toDto(service.updateAndReturnWithDetails(mapper.toEntity(menuDto))));
    }
}
