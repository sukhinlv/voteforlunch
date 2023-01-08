package ru.jsft.voteforlunch.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(
        indexes = {
                @Index(name = "menu_restaurant_id_idx", columnList = "RESTAURANT_ID"),
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_menu_date_of_menu", columnNames = {"DATE_OF_MENU", "RESTAURANT_ID"})
        })
@ToString(callSuper = true)
public class Menu extends AbstractEntity {
    @NotNull
    @Column(name = "date_of_menu", nullable = false)
    private LocalDate dateOfMenu;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Restaurant restaurant;

    @NotEmpty
    @OneToMany(mappedBy = "menu", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Set<MenuItem> menuItems = new TreeSet<>();

    public void addMenuItem(MenuItem menuItem) {
        this.menuItems.add(menuItem);
        menuItem.setMenu(this);
    }

    public void removeMenuItem(MenuItem menuItem) {
        this.menuItems.remove(menuItem);
        menuItem.setMenu(null);
    }
}
