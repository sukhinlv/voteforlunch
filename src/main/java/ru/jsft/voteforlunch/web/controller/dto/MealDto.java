package ru.jsft.voteforlunch.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jsft.voteforlunch.validation.NoHtml;

import javax.validation.constraints.NotBlank;

/**
 * A DTO for the {@link ru.jsft.voteforlunch.model.Meal} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealDto {
    private Long id;

    @NotBlank(message = "The meal must have a name")
    @NoHtml
    private String name;
}