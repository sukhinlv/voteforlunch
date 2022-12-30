package ru.jsft.voteforlunch.web.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.jsft.voteforlunch.model.Menu;

import java.time.LocalDate;

/**
 * A DTO for the {@link Menu} entity
 */
@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MenuListDto extends AbstractDto {
    @NotNull LocalDate dateOfMenu;
    long restaurantId;
    @NotNull String restaurantName;

    public MenuListDto(Long id, LocalDate dateOfMenu, long restaurantId, String restaurantName) {
        super(id);
        this.dateOfMenu = dateOfMenu;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }
}
