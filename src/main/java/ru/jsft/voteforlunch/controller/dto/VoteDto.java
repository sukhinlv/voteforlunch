package ru.jsft.voteforlunch.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
public class VoteDto extends AbstractDto {

    @NotNull
    private Long userId;
    @NotNull
    private Long restaurantId;
    @NotNull
    private LocalDate voteDate;
    @NotNull
    private LocalTime voteTime;
}
