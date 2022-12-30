package ru.jsft.voteforlunch.web.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

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

    public VoteDto(Long id, RestaurantDto restaurant, LocalDate voteDate, LocalTime voteTime) {
        super(id);
        this.restaurant = restaurant;
        this.voteDate = voteDate;
        this.voteTime = voteTime;
    }
}
