package ru.jsft.voteforlunch.controller.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.controller.dto.RestaurantDto;
import ru.jsft.voteforlunch.model.Restaurant;

@Component
public class RestaurantMapper extends AbstractMapper<Restaurant, RestaurantDto>{

    @Autowired
    RestaurantMapper() {
        super(Restaurant.class, RestaurantDto.class);
    }
}
