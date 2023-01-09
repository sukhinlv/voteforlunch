package ru.jsft.voteforlunch.web.controller.mapper;

import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.model.Dish;
import ru.jsft.voteforlunch.web.controller.dto.DishDto;

@Component
public class DishMapper implements Mapper<Dish, DishDto> {
    @Override
    public Dish toEntity(DishDto dto) {
        Dish dish = new Dish();
        dish.setId(dto.getId());
        dish.setName(dto.getName());
        return dish;
    }

    @Override
    public DishDto toDto(Dish entity) {
        return new DishDto(entity.getId(), entity.getName());
    }
}
