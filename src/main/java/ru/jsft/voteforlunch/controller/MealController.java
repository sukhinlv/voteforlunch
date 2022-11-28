package ru.jsft.voteforlunch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.jsft.voteforlunch.controller.dto.MealDto;
import ru.jsft.voteforlunch.controller.mapper.MealMapper;
import ru.jsft.voteforlunch.model.Meal;
import ru.jsft.voteforlunch.service.MealService;

import javax.validation.Valid;
import java.net.URI;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(value = MealController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealController {
    public static final String REST_URL = "/api/v1/meals";

    private final MealService service;
    private final MealMapper mapper;

    public MealController(MealService service, MealMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<MealDto>> getAll() {
        return ResponseEntity.ok(service.findAllSorted().stream()
                .map(mapper::toDto)
                .sorted(Comparator.comparing(MealDto::getName))
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealDto> get(@PathVariable long id) {
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MealDto> create(@Valid @RequestBody MealDto mealDto) {
        Meal created = service.create(mapper.toEntity(mealDto));
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
    public ResponseEntity<MealDto> update(@PathVariable long id, @Valid @RequestBody MealDto mealDto) {
        return ResponseEntity.ok(mapper.toDto(service.update(id, mapper.toEntity(mealDto))));
    }
}
