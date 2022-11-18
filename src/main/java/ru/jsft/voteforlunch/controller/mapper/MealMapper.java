package ru.jsft.voteforlunch.controller.mapper;

import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.controller.dto.MealDto;
import ru.jsft.voteforlunch.model.Meal;

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
        MealDto mealDto = new MealDto();
        mealDto.setId(entity.getId());
        mealDto.setName(entity.getName());
        return mealDto;
    }
}
