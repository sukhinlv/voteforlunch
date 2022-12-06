package ru.jsft.voteforlunch.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * A DTO for the {@link ru.jsft.voteforlunch.model.MealPrice} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealPriceDto {
    private Long id;

    @NotNull
    private MealDto meal;

    @Positive
    private int price;
}