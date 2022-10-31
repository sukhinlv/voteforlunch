package ru.jsft.voteforlunch.restaurant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.jsft.voteforlunch.basemodel.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "restaurant")
public class Restaurant extends BaseEntity<Long> {
    @NotBlank(message = "The restaurant must have a name")
    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
