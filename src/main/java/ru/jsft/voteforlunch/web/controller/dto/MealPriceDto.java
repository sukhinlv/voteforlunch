package ru.jsft.voteforlunch.web.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * A DTO for the {@link ru.jsft.voteforlunch.model.MealPrice} entity
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class MealPriceDto extends AbstractDto {
    @NotNull MealDto meal;

    @Positive int price;
}
