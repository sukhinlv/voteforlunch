package ru.jsft.voteforlunch.model;

import lombok.*;
import ru.jsft.voteforlunch.validation.NoHtml;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString(callSuper = true)
public class Meal extends AbstractEntity {
    @NotBlank(message = "The meal must have a name")
    @Column(name = "name", nullable = false, unique = true)
    @NoHtml
    private String name;
}
