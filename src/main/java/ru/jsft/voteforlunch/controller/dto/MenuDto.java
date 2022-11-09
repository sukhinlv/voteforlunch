package ru.jsft.voteforlunch.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.jsft.voteforlunch.model.MealPrice;
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
@EqualsAndHashCode(callSuper = true)
public class MenuDto extends AbstractDto {

    @NotNull
    private LocalDate dateOfMenu;

    @NotNull
    private RestaurantDto restaurant;

    private Set<MealPrice> mealPrice;
}
