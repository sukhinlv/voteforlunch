package ru.jsft.voteforlunch.web.controller.mapper;

import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.model.Restaurant;
import ru.jsft.voteforlunch.web.controller.dto.RestaurantDto;

@Component
public class RestaurantMapper implements Mapper<Restaurant, RestaurantDto> {
    @Override
    public Restaurant toEntity(RestaurantDto dto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(dto.getId());
        restaurant.setName(dto.getName());
        return restaurant;
    }

    @Override
    public RestaurantDto toDto(Restaurant entity) {
        RestaurantDto restaurantDto = new RestaurantDto(entity.getId(), entity.getName());
        return restaurantDto;
    }
}
