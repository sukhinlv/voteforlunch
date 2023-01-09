package ru.jsft.voteforlunch.web.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.jsft.voteforlunch.validation.NoHtml;

/**
 * A DTO for the {@link ru.jsft.voteforlunch.model.Dish} entity
 */
@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DishDto extends AbstractDto {
    @NotBlank(message = "The dish must have a name")
    @NoHtml
    String name;

    public DishDto(Long id, String name) {
        super(id);
        this.name = name;
    }
}
