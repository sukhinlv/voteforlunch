package ru.jsft.voteforlunch.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import ru.jsft.voteforlunch.validation.NoHtml;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString(callSuper = true)
public class Dish extends AbstractEntity {
    @NotBlank(message = "The dish must have a name")
    @Column(name = "name", nullable = false, unique = true)
    @NoHtml
    private String name;
}
