package ru.jsft.voteforlunch.meal;

import lombok.*;
import ru.jsft.voteforlunch.basemodel.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "meal")
public class Meal extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
