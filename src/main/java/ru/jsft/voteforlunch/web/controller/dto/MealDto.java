package ru.jsft.voteforlunch.web.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.jsft.voteforlunch.validation.NoHtml;

/**
 * A DTO for the {@link ru.jsft.voteforlunch.model.Meal} entity
 */
@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MealDto extends AbstractDto {
    @NotBlank(message = "The meal must have a name")
    @NoHtml
    String name;

    public MealDto(Long id, String name) {
        super(id);
        this.name = name;
    }
}
