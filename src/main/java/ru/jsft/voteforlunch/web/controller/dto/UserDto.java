package ru.jsft.voteforlunch.web.controller.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.jsft.voteforlunch.model.Role;
import ru.jsft.voteforlunch.validation.NoHtml;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Value
@EqualsAndHashCode(callSuper = true)
public class UserDto extends AbstractDto {
    @Size(max = 128)
    @NoHtml
    @Email(message = "Please enter valid e-mail")
    String email;

    @Size(max = 128)
    @NoHtml
    @NotBlank(message = "First name must not be empty")
    String firstName;

    @Size(max = 128)
    @NoHtml
    @NotBlank(message = "Last name must not be empty")
    String lastName;

    @Size(max = 256)
    @NotBlank(message = "Password must not be empty")
    String password;

    boolean enabled;

    @NotEmpty Set<Role> roles;
}
