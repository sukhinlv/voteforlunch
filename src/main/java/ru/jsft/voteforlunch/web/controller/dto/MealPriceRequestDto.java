package ru.jsft.voteforlunch.web.controller.dto;

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
public class MealPriceRequestDto extends AbstractDto {
    long mealId;

    @Positive int price;
}
