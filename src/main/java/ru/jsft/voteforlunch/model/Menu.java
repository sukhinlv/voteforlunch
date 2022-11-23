package ru.jsft.voteforlunch.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uc_menu_date_of_menu", columnNames = {"date_of_menu", "restaurant_id"})
})
@ToString
public class Menu extends AbstractEntity {
    @NotNull
    @Column(name = "date_of_menu", nullable = false)
    private LocalDate dateOfMenu;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Restaurant restaurant;

    @NotNull
    @OneToMany(mappedBy = "menu", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Set<MealPrice> mealPrice = new LinkedHashSet<>();

    public MealPrice addMealPrice(MealPrice mealPrice) {
        this.mealPrice.add(mealPrice);
        mealPrice.setMenu(this);
        return mealPrice;
    }

    public void removeMealPrice(MealPrice mealPrice) {
        this.mealPrice.remove(mealPrice);
        mealPrice.setMenu(null);
    }
}
