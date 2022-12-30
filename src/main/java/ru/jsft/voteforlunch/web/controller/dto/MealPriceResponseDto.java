package ru.jsft.voteforlunch.web.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

/**
 * A DTO for the {@link ru.jsft.voteforlunch.model.MealPrice} entity
 */
@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MealPriceResponseDto extends AbstractDto {
    @NotNull MealDto meal;

    @Positive int price;

    public MealPriceResponseDto(Long id, MealDto meal, int price) {
        super(id);
        this.meal = meal;
        this.price = price;
    }
}
