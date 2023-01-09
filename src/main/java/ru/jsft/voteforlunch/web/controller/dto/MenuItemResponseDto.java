package ru.jsft.voteforlunch.web.controller.dto;

import jakarta.validation.constraints.NotNull;
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
public class MenuItemResponseDto extends AbstractDto {
    @NotNull DishDto dish;

    @Positive(message = "Price must be positive")
    int price;

    public MenuItemResponseDto(Long id, DishDto dish, int price) {
        super(id);
        this.dish = dish;
        this.price = price;
    }
}
