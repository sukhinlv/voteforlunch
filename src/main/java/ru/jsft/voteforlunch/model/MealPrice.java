package ru.jsft.voteforlunch.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class MealPrice extends AbstractEntity implements Comparable<MealPrice> {
    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @NotNull
    @Positive
    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    private Integer price;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Menu menu;

    @Override
    public int compareTo(MealPrice comparedMealPrice) {
        if (meal == null || comparedMealPrice.getMeal() == null) {
            return 0;
        }
        return comparedMealPrice.getMeal().getName().compareTo(meal.getName());
    }
}
