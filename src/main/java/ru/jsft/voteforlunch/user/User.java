package ru.jsft.voteforlunch.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.jsft.voteforlunch.basemodel.BaseEntity;
import ru.jsft.voteforlunch.util.validation.NoHtml;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @NotBlank(message = "User name must not be empty")
    @Column(name = "name", nullable = false)
    @NoHtml
    private String name;

    @NotBlank(message = "Password must not be empty")
    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NoHtml
    private String password;

    @Email(message = "Please enter valid e-mail")
    @NotBlank(message = "Email must not be empty")
    @Column(name = "email", nullable = false, unique = true)
    @NoHtml
    private String email;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled;

    @Setter(AccessLevel.NONE)
    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDate registered;

    @NotNull
    @ElementCollection
    @Column(name = "role")
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Role> role = new LinkedHashSet<>();
}
