package ru.jsft.voteforlunch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uc_menu_date_of_menu", columnNames = {"date_of_menu", "restaurant_id"})
})
public class Menu extends AbstractEntity {
    @NotNull
    @Column(name = "date_of_menu", nullable = false)
    private LocalDate dateOfMenu;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mealPriceId")
    private Set<MealPrice> mealPrice = new HashSet<>();
}
