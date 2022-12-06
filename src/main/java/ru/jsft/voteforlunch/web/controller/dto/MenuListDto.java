package ru.jsft.voteforlunch.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jsft.voteforlunch.model.Menu;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * A DTO for the {@link Menu} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuListDto {
    private Long id;

    @NotNull
    private LocalDate dateOfMenu;

    @NotNull
    private Long restaurantId;

    @NotNull
    private String restaurantName;
}