package ru.jsft.voteforlunch.web.controller.dto;

import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.jsft.voteforlunch.model.MenuItem;

/**
 * A DTO for the {@link MenuItem} entity
 */
@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MenuItemRequestDto extends AbstractDto {
    long mealId;

    @Positive int price;
}
