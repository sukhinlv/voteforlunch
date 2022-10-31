package ru.jsft.voteforlunch.menu;

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
@Getter
@Setter
@Entity
@Table(name = "menu", uniqueConstraints = {
        @UniqueConstraint(name = "uc_menu_date_of_menu_id", columnNames = {"date_of_menu", "id"})
})
public class Menu extends BaseEntity<Long> {
    @NotNull
    @Column(name = "date_of_menu", nullable = false)
    private LocalDate dateOfMenu;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "menu_id", nullable = false)
    private Set<MealWithPrice> mealWithPrices = new LinkedHashSet<>();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}
