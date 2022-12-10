package ru.jsft.voteforlunch.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.jsft.voteforlunch.model.Role;
import ru.jsft.voteforlunch.validation.NoHtml;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @Size(max = 128)
    @NoHtml
    @Email(message = "Please enter valid e-mail")
    @NotBlank(message = "Email must not be empty")
    private String email;

    @Size(max = 128)
    @NoHtml
    @NotBlank(message = "First name must not be empty")
    private String firstName;

    @Size(max = 128)
    @NoHtml
    @NotBlank(message = "Last name must not be empty")
    private String lastName;

    @Size(max = 256)
    @NotBlank(message = "Password must not be empty")
    private String password;

    private boolean enabled;

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
