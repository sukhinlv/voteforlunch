package ru.jsft.voteforlunch.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jsft.voteforlunch.validation.NoHtml;

import javax.validation.constraints.NotBlank;

/**
 * A DTO for the {@link ru.jsft.voteforlunch.model.Restaurant} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {
    private Long id;

    @NotBlank(message = "The restaurant must have a name")
    @NoHtml
    private String name;
}
