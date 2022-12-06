package ru.jsft.voteforlunch.web.controller.mapper;

import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.model.User;
import ru.jsft.voteforlunch.web.controller.dto.UserDto;

@Component
public class UserMapper implements Mapper<User, UserDto> {
    @Override
    public User toEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setEnabled(dto.isEnabled());
        user.setRoles(dto.getRoles());
        return user;
    }

    @Override
    public UserDto toDto(User entity) {
        UserDto userDto = new UserDto();
        userDto.setId(entity.getId());
        userDto.setName(entity.getName());
        userDto.setEmail(entity.getEmail());
        userDto.setEnabled(entity.isEnabled());
        userDto.setRoles(entity.getRoles());
        return userDto;
    }
}
