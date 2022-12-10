package ru.jsft.voteforlunch.web.controller.mapper;

import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.model.User;
import ru.jsft.voteforlunch.web.controller.dto.UserDto;

@Component
public class UserMapper implements Mapper<User, UserDto> {
    @Override
    public User toEntity(UserDto dto) {
        User user = new User(
                dto.getEmail(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getPassword(),
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
                entity.getPassword(),
                entity.isEnabled(),
                entity.getRoles()
        );
    }
}
