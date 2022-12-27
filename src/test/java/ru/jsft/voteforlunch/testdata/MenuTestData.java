package ru.jsft.voteforlunch.testdata;

import ru.jsft.voteforlunch.model.Menu;

import java.time.LocalDate;

public class MenuTestData {
    public static LocalDate YESTERDAY = LocalDate.of(2022, 11, 14);
    public static LocalDate TODAY = LocalDate.of(2022, 11, 15);

    public static Long MENU_FOR_CHERRY1_ID = 1L;
    public static Long MENU_FOR_CHERRY2_ID = 2L;
    public static Long MENU_FOR_AISHA1_ID = 3L;
    public static Long MENU_FOR_AISHA2_ID = 4L;

    public static Menu MENU_FOR_CHERRY1 = new Menu();
    public static Menu MENU_FOR_CHERRY2 = new Menu();

    public static Menu MENU_FOR_AISHA1 = new Menu();
    public static Menu MENU_FOR_AISHA2 = new Menu();
}
