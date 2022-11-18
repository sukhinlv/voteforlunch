package ru.jsft.voteforlunch.controller.mapper;

import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.controller.dto.RestaurantDto;
import ru.jsft.voteforlunch.model.Restaurant;

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
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setId(entity.getId());
        restaurantDto.setName(entity.getName());
        return restaurantDto;
    }
}
