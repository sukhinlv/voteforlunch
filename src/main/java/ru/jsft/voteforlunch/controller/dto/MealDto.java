package ru.jsft.voteforlunch.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * A DTO for the {@link ru.jsft.voteforlunch.model.Meal} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MealDto extends AbstractDto {

    @NotBlank(message = "The meal must have a name")
    private String name;
}