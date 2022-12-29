package ru.jsft.voteforlunch.web.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.jsft.voteforlunch.validation.NoHtml;

/**
 * A DTO for the {@link ru.jsft.voteforlunch.model.Restaurant} entity
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantDto extends AbstractDto {
    @NotBlank(message = "The restaurant must have a name")
    @NoHtml
    String name;

    // You should have such hand-made AllArgsConstructor while using @Value because otherwise there will be deserialization problem
    // RestaurantDto used in VoteDto, so if such constructor is absent, "Cannot construct instance of..." error occurs
    public RestaurantDto(Long id, String name) {
        super(id);
        this.name = name;
    }
}
