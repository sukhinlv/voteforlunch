package ru.jsft.voteforlunch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jsft.voteforlunch.controller.dto.RestaurantDto;
import ru.jsft.voteforlunch.controller.mapper.RestaurantMapper;
import ru.jsft.voteforlunch.service.RestaurantService;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("api/v1/restaurants")
public class RestaurantController {

    private final RestaurantService service;

    private final RestaurantMapper mapper;

    public RestaurantController(RestaurantService service, RestaurantMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getAll() {
        return ResponseEntity.ok(service.findAll().stream()
                .map(mapper::toDto)
                .sorted(Comparator.comparing(RestaurantDto::getName))
                .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> get(@PathVariable long id) {
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<RestaurantDto> create(@Valid @RequestBody RestaurantDto restaurantDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(service.create(mapper.toEntity(restaurantDto))));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<RestaurantDto> update(@PathVariable long id, @Valid @RequestBody RestaurantDto restaurantDto) {
        return ResponseEntity.ok(mapper.toDto(service.update(id, mapper.toEntity(restaurantDto))));
    }
}
