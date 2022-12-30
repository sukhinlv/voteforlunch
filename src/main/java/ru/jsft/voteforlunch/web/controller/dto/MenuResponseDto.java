package ru.jsft.voteforlunch.web.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.jsft.voteforlunch.model.Menu;

import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO for the {@link Menu} entity
 */
@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MenuResponseDto extends AbstractDto {
    @NotNull LocalDate dateOfMenu;

    @NotNull RestaurantDto restaurant;

    Set<MealPriceResponseDto> mealPrices;

    public MenuResponseDto(Long id, LocalDate dateOfMenu, RestaurantDto restaurant, Set<MealPriceResponseDto> mealPrices) {
        super(id);
        this.dateOfMenu = dateOfMenu;
        this.restaurant = restaurant;
        this.mealPrices = mealPrices;
    }
}
