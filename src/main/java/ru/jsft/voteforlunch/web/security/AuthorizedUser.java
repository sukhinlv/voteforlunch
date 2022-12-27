package ru.jsft.voteforlunch.web.security;

import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;
import ru.jsft.voteforlunch.model.User;

@Getter
@ToString(of = "user")
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private final User user;

    public AuthorizedUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public long id() {
        return user.getId();
    }
}
