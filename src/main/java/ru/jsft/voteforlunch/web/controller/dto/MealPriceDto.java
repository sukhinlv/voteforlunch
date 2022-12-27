package ru.jsft.voteforlunch.web.controller.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * A DTO for the {@link ru.jsft.voteforlunch.model.MealPrice} entity
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class MealPriceDto extends AbstractDto {
    @NotNull MealDto meal;

    @Positive int price;
}
