package ru.jsft.voteforlunch.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(
        indexes = {
                @Index(name = "menu_item_meal_id_idx", columnList = "MEAL_ID"),
                @Index(name = "menu_item_menu_id_idx", columnList = "MENU_ID")
        }
)
public class MenuItem extends AbstractEntity implements Comparable<MenuItem> {
    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @NotNull
    @Positive(message = "Price must be positive")
    @Column(name = "price", nullable = false)
    private Integer price;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Menu menu;

    @Override
    public int compareTo(MenuItem comparedMenuItem) {
        if (meal == null || comparedMenuItem.getMeal() == null) {
            return 0;
        }
        return comparedMenuItem.getMeal().getName().compareTo(meal.getName());
    }
}


