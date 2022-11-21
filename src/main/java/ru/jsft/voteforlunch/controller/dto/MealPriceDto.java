package ru.jsft.voteforlunch.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * A DTO for the {@link ru.jsft.voteforlunch.model.MealPrice} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MealPriceDto extends AbstractDto {

    @NotNull
    private MealDto meal;

    @Positive
    private int price;
}