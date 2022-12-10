package ru.jsft.voteforlunch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.jsft.voteforlunch.validation.NoHtml;
import ru.jsft.voteforlunch.web.security.PasswordDeserializer;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"password"})
public class User extends AbstractEntity implements Serializable {

    @Column(name = "email", nullable = false, unique = true)
    @Size(max = 128)
    @NoHtml
    @Email(message = "Please enter valid e-mail")
    @NotBlank(message = "Email must not be empty")
    private String email;

    @Column(name = "first_name")
    @Size(max = 128)
    @NoHtml
    @NotBlank(message = "First name must not be empty")
    private String firstName;

    @Column(name = "last_name")
    @Size(max = 128)
    @NoHtml
    @NotBlank(message = "Last name must not be empty")
    private String lastName;

    @Column(name = "password")
    @Size(max = 256)
    @NotBlank(message = "Password must not be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonDeserialize(using = PasswordDeserializer.class)
    private String password;

    private boolean enabled;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public void setEmail(String email) {
        this.email = StringUtils.hasText(email) ? email.toLowerCase() : null;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }
}
