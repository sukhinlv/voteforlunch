package ru.jsft.voteforlunch.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Embeddable
public class MealPrice {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @NotNull
    @Positive
    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    private Integer price;
}
