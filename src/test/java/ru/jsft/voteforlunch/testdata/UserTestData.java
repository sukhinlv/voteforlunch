package ru.jsft.voteforlunch.testdata;

import ru.jsft.voteforlunch.model.Role;
import ru.jsft.voteforlunch.model.User;
import ru.jsft.voteforlunch.utils.MatcherFactory;
import ru.jsft.voteforlunch.web.controller.dto.UserDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserTestData {
    public static MatcherFactory.Matcher<UserDto> USER_DTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(UserDto.class, "password");

    public static List<UserDto> USER_DTO_LIST = new ArrayList<>(2002);

    public static User ADMIN = new User("admin@ya.ru", "admin", "admin", "admin", true, Collections.singleton(Role.ADMIN));
    public static UserDto ADMIN_DTO = new UserDto(1L, ADMIN.getEmail(), ADMIN.getFirstName(), ADMIN.getLastName(), "***", ADMIN.isEnabled(), ADMIN.getRoles());
    public static User USER = new User("user@ya.ru", "user", "user", "user", true, Collections.singleton(Role.USER));
    public static UserDto USER_DTO = new UserDto(2L, USER.getEmail(), USER.getFirstName(), USER.getLastName(), "***", USER.isEnabled(), USER.getRoles());

    static {
        USER_DTO_LIST.add(ADMIN_DTO);
        USER_DTO_LIST.add(USER_DTO);
        for (long i = 0; i < 2000; i++) {
            USER_DTO_LIST.add(new UserDto(i + 3, "user" + i + "@gmail.com", "userName" + i, "userSurname" + i, "***", true, Collections.singleton(Role.USER)));
        }
        USER_DTO_LIST = USER_DTO_LIST.stream().sorted(Comparator.comparing(UserDto::getEmail)).toList();
    }

    public static UserDto getNewDto() {
        return new UserDto(null, "new@gmail.com", "newusername", "newsurname", "***", true, Collections.singleton(Role.USER));
    }

    public static UserDto getUpdatedDto() {
        return new UserDto(
                USER_DTO.getId(),
                USER_DTO.getEmail(),
                USER_DTO.getFirstName(),
                USER_DTO.getLastName(),
                USER_DTO.getPassword(),
                USER_DTO.isEnabled(),
                USER_DTO.getRoles()
        );
    }
}
