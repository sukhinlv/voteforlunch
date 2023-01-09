package ru.jsft.voteforlunch.web.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.jsft.voteforlunch.model.Dish;
import ru.jsft.voteforlunch.service.DishService;
import ru.jsft.voteforlunch.web.controller.dto.DishDto;
import ru.jsft.voteforlunch.web.controller.mapper.DishMapper;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {
    public static final String REST_URL = "/api/v1/dishes";

    private final DishService service;
    private final DishMapper mapper;

    public DishController(DishService service, DishMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<DishDto>> getAll() {
        return ResponseEntity.ok(service.findAllSorted().stream()
                .map(mapper::toDto)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishDto> get(@PathVariable long id) {
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishDto> create(@Valid @RequestBody DishDto dishDto) {
        Dish created = service.create(mapper.toEntity(dishDto));
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

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishDto> update(@PathVariable long id, @Valid @RequestBody DishDto dishDto) {
        return ResponseEntity.ok(mapper.toDto(service.update(id, mapper.toEntity(dishDto))));
    }
}
