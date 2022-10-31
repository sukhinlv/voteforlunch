package ru.jsft.voteforlunch.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.jsft.voteforlunch.basemodel.BaseEntity;
import ru.jsft.voteforlunch.restaurant.Restaurant;

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
@Table(name = "menu")
public class Menu extends BaseEntity<Long> {
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
