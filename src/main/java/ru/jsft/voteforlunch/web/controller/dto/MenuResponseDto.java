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

    Set<MenuItemResponseDto> menuItems;

    public MenuResponseDto(Long id, LocalDate dateOfMenu, RestaurantDto restaurant, Set<MenuItemResponseDto> menuItems) {
        super(id);
        this.dateOfMenu = dateOfMenu;
        this.restaurant = restaurant;
        this.menuItems = menuItems;
    }
}
