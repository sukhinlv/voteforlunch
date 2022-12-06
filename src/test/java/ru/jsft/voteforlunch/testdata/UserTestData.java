package ru.jsft.voteforlunch.testdata;

import ru.jsft.voteforlunch.model.Role;
import ru.jsft.voteforlunch.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserTestData {
    public static List<User> USER_LIST = new ArrayList<>(2000);

    public static User ADMIN = new User("admin", "admin", "admin@ya.ru", true,
            LocalDate.of(2022, 10, 15), Collections.singleton(Role.ADMIN));
    public static User USER = new User("user", "user", "user@gmail.com", true,
            LocalDate.of(2022, 10, 20), Collections.singleton(Role.USER));

    static {
        USER_LIST.add(ADMIN);
        USER_LIST.add(USER);
        for (int i = 0; i < 1998; i++) {
            USER_LIST.add(new User("user" + i, "user" + i, "user" + i + "@gmail.com", true,
                    LocalDate.of(2022, 10, 15), Collections.singleton(Role.USER)));
        }
    }
}
