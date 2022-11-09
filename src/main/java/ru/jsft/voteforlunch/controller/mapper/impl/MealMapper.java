package ru.jsft.voteforlunch.controller.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.controller.dto.MealDto;
import ru.jsft.voteforlunch.model.Meal;

@Component
public class MealMapper extends AbstractMapper<Meal, MealDto>{

    @Autowired
    MealMapper() {
        super(Meal.class, MealDto.class);
    }
}
