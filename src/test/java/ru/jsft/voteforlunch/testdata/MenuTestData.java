package ru.jsft.voteforlunch.testdata;

import ru.jsft.voteforlunch.model.Menu;

import java.time.LocalDate;

public class MenuTestData {
    public static LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    public static LocalDate TODAY = LocalDate.now();

    public static Long MENU_FOR_CHERRY1_ID = 1L;
    public static Long MENU_FOR_CHERRY2_ID = 2L;
    public static Long MENU_FOR_AISHA1_ID = 3L;
    public static Long MENU_FOR_AISHA2_ID = 4L;

    public static Menu MENU_FOR_CHERRY1 = new Menu();
    public static Menu MENU_FOR_CHERRY2 = new Menu();

    static {
//        MENU_FOR_CHERRY1.setDateOfMenu(YESTERDAY);
//        MENU_FOR_CHERRY1.setRestaurant(CHERRY_RESTAURANT);
//        MENU_FOR_CHERRY1.setMealPrice(new TreeSet<>(Set.of(
//                new MealPrice(TEA, 10, MENU_FOR_CHERRY1),
//                new MealPrice(BURGER, 15, MENU_FOR_CHERRY1)
//        )));
//        MENU_FOR_CHERRY2.setDateOfMenu(TODAY);
//        MENU_FOR_CHERRY2.setRestaurant(CHERRY_RESTAURANT);
//        MENU_FOR_CHERRY2.setMealPrice(new TreeSet<>(Set.of(
//                new MealPrice(SOUP, 25, MENU_FOR_CHERRY2),
//                new MealPrice(BURGER, 15, MENU_FOR_CHERRY2)
//        )));
    }

    public static Menu MENU_FOR_AISHA1 = new Menu();
    public static Menu MENU_FOR_AISHA2 = new Menu();

    static {
//        MENU_FOR_AISHA1.setDateOfMenu(YESTERDAY);
//        MENU_FOR_AISHA1.setRestaurant(AISHA_RESTAURANT);
//        MENU_FOR_AISHA1.setMealPrice(new TreeSet<>(Set.of(
//                new
//
//                        MealPrice(TEA, 15, MENU_FOR_AISHA1),
//                new
//
//                        MealPrice(PASTA, 25, MENU_FOR_AISHA1)
//        )));
//
//        MENU_FOR_AISHA2.setDateOfMenu(TODAY);
//        MENU_FOR_AISHA2.setRestaurant(AISHA_RESTAURANT);
//        MENU_FOR_AISHA2.setMealPrice(new TreeSet<>(Set.of(
//                new
//
//                        MealPrice(SANDWICH, 25, MENU_FOR_AISHA2),
//                new
//
//                        MealPrice(TEA, 15, MENU_FOR_AISHA2)
//        )));
    }
}
