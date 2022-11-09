package ru.jsft.voteforlunch.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
public class MenuListDto extends AbstractDto {

    @NotNull
    private LocalDate dateOfMenu;

    @NotNull
    private Long restaurantId;

    @NotNull
    private String restaurantName;
}
