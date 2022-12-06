package ru.jsft.voteforlunch.testdata;

import ru.jsft.voteforlunch.controller.dto.RestaurantDto;

public class RestaurantTestData {
    public static long CHERRY_RESTAURANT_ID = 1L;
    public static long AISHA_RESTAURANT_ID = 1L;

    public static RestaurantDto CHERRY_RESTAURANT;
    public static RestaurantDto AISHA_RESTAURANT;

    static {
        CHERRY_RESTAURANT = new RestaurantDto(1L, "Cherry");
        AISHA_RESTAURANT = new RestaurantDto(2L, "Aisha");
    }
}
