package ru.jsft.voteforlunch.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.jsft.voteforlunch.basemodel.BaseEntity;
import ru.jsft.voteforlunch.meal.Meal;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "meal_with_price", uniqueConstraints = {
        @UniqueConstraint(name = "uc_mealwithprice_menu_id", columnNames = {"menu_id", "meal_id"})
})
public class MealWithPrice extends BaseEntity<Long> {
    @NotNull
    @Positive
    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;
}