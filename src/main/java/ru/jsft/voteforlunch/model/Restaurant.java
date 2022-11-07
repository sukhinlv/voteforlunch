package ru.jsft.voteforlunch.model;

import lombok.*;
import ru.jsft.voteforlunch.util.validation.NoHtml;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "restaurant")
@ToString(callSuper = true)
public class Restaurant extends BaseEntity {
    @NotBlank(message = "The restaurant must have a name")
    @Column(name = "name", nullable = false, unique = true)
    @NoHtml
    private String name;
}
