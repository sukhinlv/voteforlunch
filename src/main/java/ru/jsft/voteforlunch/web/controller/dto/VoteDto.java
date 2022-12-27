package ru.jsft.voteforlunch.web.controller.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * A DTO for the {@link ru.jsft.voteforlunch.model.Vote} entity
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class VoteDto extends AbstractDto {
    @NotNull RestaurantDto restaurant;

    @NotNull LocalDate voteDate;

    @NotNull LocalTime voteTime;
}
