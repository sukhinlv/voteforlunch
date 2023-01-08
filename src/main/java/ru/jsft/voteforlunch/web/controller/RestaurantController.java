package ru.jsft.voteforlunch.web.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.jsft.voteforlunch.model.Restaurant;
import ru.jsft.voteforlunch.service.RestaurantService;
import ru.jsft.voteforlunch.web.controller.dto.RestaurantDto;
import ru.jsft.voteforlunch.web.controller.mapper.RestaurantMapper;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    public static final String REST_URL = "/api/v1/restaurants";

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
                .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> get(@PathVariable long id) {
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantDto> create(@Valid @RequestBody RestaurantDto restaurantDto) {
        Restaurant created = service.create(mapper.toEntity(restaurantDto));
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
    public ResponseEntity<RestaurantDto> update(@PathVariable long id, @Valid @RequestBody RestaurantDto restaurantDto) {
        return ResponseEntity.ok(mapper.toDto(service.update(id, mapper.toEntity(restaurantDto))));
    }
}
