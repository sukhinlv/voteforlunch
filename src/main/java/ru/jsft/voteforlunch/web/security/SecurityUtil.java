package ru.jsft.voteforlunch.web.security;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityUtil {

    private static int id = 1;

    public static int authUserId() {
        return id;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }
}
