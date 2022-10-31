package ru.jsft.voteforlunch.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.jsft.voteforlunch.basemodel.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity<Long> {
    @NotBlank(message = "User name must not be empty")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Password must not be empty")
    @Column(name = "password", nullable = false)
    private String password;

    @Email(message = "Please enter valid e-mail")
    @NotBlank(message = "Email must not be empty")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled;

    @Setter(AccessLevel.NONE)
    @Temporal(TemporalType.DATE)
    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    private Date registered;

    @NotNull
    @ElementCollection
    @Column(name = "role")
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Role> role = new LinkedHashSet<>();
}
