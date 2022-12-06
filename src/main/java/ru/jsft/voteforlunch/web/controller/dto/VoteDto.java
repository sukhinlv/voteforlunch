package ru.jsft.voteforlunch.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * A DTO for the {@link ru.jsft.voteforlunch.model.Vote} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteDto {
    private Long id;

    @NotNull
    private RestaurantDto restaurant;

    @NotNull
    private LocalDate voteDate;

    @NotNull
    private LocalTime voteTime;
}
