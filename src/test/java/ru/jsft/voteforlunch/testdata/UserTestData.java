package ru.jsft.voteforlunch.testdata;

import ru.jsft.voteforlunch.model.Role;
import ru.jsft.voteforlunch.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserTestData {
    public static List<User> USER_LIST = new ArrayList<>(2000);

    public static User ADMIN = new User("admin@ya.ru", "admin", "admin", "admin", true, Collections.singleton(Role.ADMIN));
    public static User USER = new User("user@ya.ru", "user", "user", "user", true, Collections.singleton(Role.USER));

    static {
        USER_LIST.add(ADMIN);
        USER_LIST.add(USER);
        for (int i = 0; i < 1998; i++) {
            USER_LIST.add(new User("user" + i + "@gmail.com", "userName" + i, "userSurname" + i, "user" + i, true, Collections.singleton(Role.USER)));
        }
    }
}
