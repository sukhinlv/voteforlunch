package ru.jsft.voteforlunch.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.jsft.voteforlunch.basemodel.BaseEntity;
import ru.jsft.voteforlunch.mealwithprice.MealWithPrice;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "menu")
public class Menu extends BaseEntity {
    @Column(name = "date_of_menu", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfMenu;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "menu_id")
    private List<MealWithPrice> mealWithPrices = new ArrayList<>();
}
