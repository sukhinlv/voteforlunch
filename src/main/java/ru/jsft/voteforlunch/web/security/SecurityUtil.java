package ru.jsft.voteforlunch.web.security;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityUtil {

    private static long id = 1L;

    public static long authUserId() {
        return id;
    }

    public static void setAuthUserId(long id) {
        SecurityUtil.id = id;
    }}
