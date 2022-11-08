package ru.jsft.voteforlunch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jsft.voteforlunch.controller.dto.RestaurantDto;
import ru.jsft.voteforlunch.controller.mapper.impl.RestaurantMapper;
import ru.jsft.voteforlunch.model.Restaurant;
import ru.jsft.voteforlunch.service.RestaurantService;

import javax.validation.Valid;
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
    public List<Restaurant> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(service.get(id)));
    }

    @PostMapping
    public ResponseEntity<RestaurantDto> create(@Valid @RequestBody RestaurantDto restaurantDto) {
        return ResponseEntity.ok(mapper.toDto(service.create(mapper.toEntity(restaurantDto))));
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<RestaurantDto> update(@PathVariable Long id, @Valid @RequestBody RestaurantDto restaurantDto) {
        return ResponseEntity.ok(mapper.toDto(service.update(id, mapper.toEntity(restaurantDto))));
    }
}
