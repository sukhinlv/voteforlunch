package ru.jsft.voteforlunch.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jsft.voteforlunch.model.Menu;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO for the {@link Menu} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {
    private Long id;

    @NotNull
    private LocalDate dateOfMenu;

    @NotNull
    private RestaurantDto restaurant;

    private Set<MealPriceDto> mealPrice;
}
