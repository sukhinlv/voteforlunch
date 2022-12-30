package ru.jsft.voteforlunch.web.controller.mapper;

import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.model.Meal;
import ru.jsft.voteforlunch.web.controller.dto.MealDto;

@Component
public class MealMapper implements Mapper<Meal, MealDto> {
    @Override
    public Meal toEntity(MealDto dto) {
        Meal meal = new Meal();
        meal.setId(dto.getId());
        meal.setName(dto.getName());
        return meal;
    }

    @Override
    public MealDto toDto(Meal entity) {
        return new MealDto(entity.getId(), entity.getName());
    }
}
