package ru.jsft.voteforlunch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.jsft.voteforlunch.util.validation.NoHtml;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Restaurant extends AbstractEntity {
    @NotBlank(message = "The restaurant must have a name")
    @Column(name = "name", nullable = false, unique = true)
    @NoHtml
    private String name;
}
