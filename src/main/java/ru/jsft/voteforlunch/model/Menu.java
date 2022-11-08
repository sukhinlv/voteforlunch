package ru.jsft.voteforlunch.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "menu", uniqueConstraints = {
        @UniqueConstraint(name = "uc_menu_date_of_menu", columnNames = {"date_of_menu", "restaurant_id"})
})
@ToString(callSuper = true)
public class Menu extends AbstractEntity {
    @NotNull
    @Column(name = "date_of_menu", nullable = false)
    private LocalDate dateOfMenu;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ElementCollection
    @CollectionTable(name = "menu_meal_price", joinColumns = @JoinColumn(name = "menu_id"))
    private Set<MealPrice> mealPrice = new LinkedHashSet<>();
}
