package ru.jsft.voteforlunch.web.controller.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.model.User;
import ru.jsft.voteforlunch.web.controller.dto.UserDto;

@Component
public class UserMapper implements Mapper<User, UserDto> {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User toEntity(UserDto dto) {
        User user = new User(
                dto.getEmail(),
                dto.getFirstName(),
                dto.getLastName(),
                passwordEncoder.encode(dto.getPassword()),
                dto.isEnabled(),
                dto.getRoles()
        );
        user.setId(dto.getId());
        return user;
    }

    @Override
    public UserDto toDto(User entity) {
        return new UserDto(
                entity.getId(),
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName(),
                "***", // do not expose password to frontend
                entity.isEnabled(),
                entity.getRoles()
        );
    }
}
