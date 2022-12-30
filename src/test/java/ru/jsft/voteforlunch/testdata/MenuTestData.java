package ru.jsft.voteforlunch.testdata;

import ru.jsft.voteforlunch.utils.MatcherFactory;
import ru.jsft.voteforlunch.web.controller.dto.MealPriceResponseDto;
import ru.jsft.voteforlunch.web.controller.dto.MenuListDto;
import ru.jsft.voteforlunch.web.controller.dto.MenuResponseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static ru.jsft.voteforlunch.testdata.MealTestData.*;
import static ru.jsft.voteforlunch.testdata.RestaurantTestData.AISHA_RESTAURANT;
import static ru.jsft.voteforlunch.testdata.RestaurantTestData.CHERRY_RESTAURANT;

public class MenuTestData {
    public static MatcherFactory.Matcher<MenuListDto> MENU_LIST_DTO_MATCHER = MatcherFactory.usingEqualsComparator(MenuListDto.class);
    public static MatcherFactory.Matcher<MenuResponseDto> MENU_RESPONSE_DTO_MATCHER = MatcherFactory.usingEqualsComparator(MenuResponseDto.class);

    public static LocalDate MINUS_TWO_DAYS = LocalDate.of(2022, 11, 13);
    public static LocalDate MINUS_ONE_DAY = LocalDate.of(2022, 11, 14);

    public static MenuResponseDto MENU_FOR_CHERRY1 = new MenuResponseDto(1L, MINUS_TWO_DAYS, CHERRY_RESTAURANT,
            Set.of(new MealPriceResponseDto(1L, TEA, 10), new MealPriceResponseDto(2L, BURGER, 15)));
    public static MenuResponseDto MENU_FOR_CHERRY2 = new MenuResponseDto(2L, MINUS_ONE_DAY, CHERRY_RESTAURANT,
            Set.of(new MealPriceResponseDto(3L, SOUP, 25), new MealPriceResponseDto(4L, BURGER, 15)));
    public static MenuResponseDto MENU_FOR_AISHA1 =  new MenuResponseDto(3L, MINUS_TWO_DAYS, AISHA_RESTAURANT,
            Set.of(new MealPriceResponseDto(5L, TEA, 15), new MealPriceResponseDto(6L, PASTA, 25)));
    public static MenuResponseDto MENU_FOR_AISHA2 =  new MenuResponseDto(4L, MINUS_ONE_DAY, AISHA_RESTAURANT,
            Set.of(new MealPriceResponseDto(7L, SANDWICH, 25), new MealPriceResponseDto(8L, TEA, 25)));

    public static List<MenuListDto> MENUS_LIST = List.of(
            new MenuListDto(4L, MINUS_ONE_DAY, AISHA_RESTAURANT.getId(), AISHA_RESTAURANT.getName()),
            new MenuListDto(2L, MINUS_ONE_DAY, CHERRY_RESTAURANT.getId(), CHERRY_RESTAURANT.getName()),
            new MenuListDto(3L, MINUS_TWO_DAYS, AISHA_RESTAURANT.getId(), AISHA_RESTAURANT.getName()),
            new MenuListDto(1L, MINUS_TWO_DAYS, CHERRY_RESTAURANT.getId(), CHERRY_RESTAURANT.getName())
    );

    public static List<MenuListDto> MINUS_TWO_DAYS_MENUS_LIST = List.of(
            new MenuListDto(3L, MINUS_TWO_DAYS, AISHA_RESTAURANT.getId(), AISHA_RESTAURANT.getName()),
            new MenuListDto(1L, MINUS_TWO_DAYS, CHERRY_RESTAURANT.getId(), CHERRY_RESTAURANT.getName())
    );
}
