package ru.jsft.voteforlunch.web.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.jsft.voteforlunch.model.Menu;

import java.time.LocalDate;

/**
 * A DTO for the {@link Menu} entity
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class MenuListDto extends AbstractDto {
    @NotNull LocalDate dateOfMenu;

    @NotNull Long restaurantId;

    @NotNull String restaurantName;
}
