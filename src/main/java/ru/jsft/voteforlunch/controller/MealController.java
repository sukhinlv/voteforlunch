package ru.jsft.voteforlunch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jsft.voteforlunch.controller.dto.MealDto;
import ru.jsft.voteforlunch.controller.mapper.impl.MealMapper;
import ru.jsft.voteforlunch.model.Meal;
import ru.jsft.voteforlunch.service.MealService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/meals")
public class MealController {

    private final MealService service;

    private final MealMapper mapper;

    public MealController(MealService service, MealMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<Meal> getAll() {
        // TODO results not converted to DTO
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealDto> get(@PathVariable long id) {
        return ResponseEntity.ok(mapper.toDto(service.get(id)));
    }

    @PostMapping
    public ResponseEntity<MealDto> create(@Valid @RequestBody MealDto mealDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(service.create(mapper.toEntity(mealDto))));
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<MealDto> update(@PathVariable long id, @Valid @RequestBody MealDto mealDto) {
        return ResponseEntity.ok(mapper.toDto(service.update(id, mapper.toEntity(mealDto))));
    }
}
