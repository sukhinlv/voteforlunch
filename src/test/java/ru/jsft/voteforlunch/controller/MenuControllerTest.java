package ru.jsft.voteforlunch.controller;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import ru.jsft.voteforlunch.AbstractSpringBootTest;
import ru.jsft.voteforlunch.error.IllegalRequestDataException;
import ru.jsft.voteforlunch.service.MenuService;
import ru.jsft.voteforlunch.utils.LinkedHashMapMatcher;
import ru.jsft.voteforlunch.utils.MatcherFactory;
import ru.jsft.voteforlunch.web.controller.dto.*;
import ru.jsft.voteforlunch.web.controller.mapper.MenuMapper;
import ru.jsft.voteforlunch.web.json.JsonUtil;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.jsft.voteforlunch.testdata.MealTestData.*;
import static ru.jsft.voteforlunch.testdata.RestaurantTestData.AISHA_RESTAURANT;
import static ru.jsft.voteforlunch.testdata.RestaurantTestData.CHERRY_RESTAURANT;
import static ru.jsft.voteforlunch.testdata.UserTestData.ADMIN_MAIL;
import static ru.jsft.voteforlunch.testdata.UserTestData.USER_MAIL;
import static ru.jsft.voteforlunch.web.controller.MenuController.REST_URL;

public class MenuControllerTest extends AbstractSpringBootTest {
    private static final LinkedHashMapMatcher MENU_CONSTRAINTS_MATCHER = new LinkedHashMapMatcher(new LinkedHashMap<>(Map.of(
            "dateOfMenu", "must not be null",
            "menuItems", "must not be empty")));

    private static final LinkedHashMapMatcher MEAL_PRICE_CONSTRAINTS_MATCHER = new LinkedHashMapMatcher(new LinkedHashMap<>(Map.of(
            "price", "Price must be positive")));

    public static MatcherFactory.Matcher<MenuListDto> MENU_LIST_DTO_MATCHER = MatcherFactory.usingEqualsComparator(MenuListDto.class);
    public static MatcherFactory.Matcher<MenuResponseDto> MENU_RESPONSE_DTO_MATCHER = MatcherFactory.usingEqualsComparator(MenuResponseDto.class);

    public static LocalDate MINUS_TWO_DAYS = LocalDate.of(2022, 11, 13);
    public static LocalDate MINUS_ONE_DAY = LocalDate.of(2022, 11, 14);
    public static LocalDate TODAY = LocalDate.of(2022, 11, 15);

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuMapper mapper;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getListOfMenusOnDate() throws Exception {
        mockMvc.perform(get(REST_URL + "/on-date?date=" + MINUS_TWO_DAYS))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_LIST_DTO_MATCHER.contentJson(List.of(
                        new MenuListDto(3L, MINUS_TWO_DAYS, AISHA_RESTAURANT.getId(), AISHA_RESTAURANT.getName()),
                        new MenuListDto(1L, MINUS_TWO_DAYS, CHERRY_RESTAURANT.getId(), CHERRY_RESTAURANT.getName())
                )));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getMenu() throws Exception {
        mockMvc.perform(get(REST_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_RESPONSE_DTO_MATCHER.contentJson(new MenuResponseDto(1L, MINUS_TWO_DAYS, CHERRY_RESTAURANT,
                        Set.of(new MenuItemResponseDto(1L, TEA, 10), new MenuItemResponseDto(2L, BURGER, 15)))));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createMenu() throws Exception {
        MenuRequestDto menuRequestDto = new MenuRequestDto(TODAY, CHERRY_RESTAURANT.getId(),
                Set.of(new MenuItemRequestDto(TEA.getId(), 20), new MenuItemRequestDto(BURGER.getId(), 30)));

        ResultActions resultActions = mockMvc.perform(post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.objectToJson(menuRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        MenuResponseDto menuResponseDto = MENU_RESPONSE_DTO_MATCHER.readFromJson(resultActions);

        assertThat(menuResponseDto.getId()).isNotNull();
        assertThat(menuResponseDto.getDateOfMenu()).isEqualTo(TODAY);
        assertThat(menuResponseDto.getMenuItems().size()).isEqualTo(2);
        assertThat(menuResponseDto.getMenuItems())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(Set.of(
                        new MenuItemResponseDto(null, TEA, 20),
                        new MenuItemResponseDto(null, BURGER, 30)
                ));
        assertThat(menuResponseDto).isEqualTo(mapper.toDto(menuService.findByIdWithAllData(menuResponseDto.getId())));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateMenu() throws Exception {
        MenuRequestDto updatedMenuRequestDto = new MenuRequestDto(TODAY.plusDays(1), AISHA_RESTAURANT.getId(),
                Set.of(new MenuItemRequestDto(SOUP.getId(), 15), new MenuItemRequestDto(SANDWICH.getId(), 25)));

        ResultActions resultActions = mockMvc.perform(put(REST_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.objectToJson(updatedMenuRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        MenuResponseDto menuResponseDto = MENU_RESPONSE_DTO_MATCHER.readFromJson(resultActions);

        assertThat(menuResponseDto.getId()).isEqualTo(1L);
        assertThat(menuResponseDto.getDateOfMenu()).isEqualTo(TODAY.plusDays(1));
        assertThat(menuResponseDto.getMenuItems().size()).isEqualTo(2);
        assertThat(menuResponseDto.getMenuItems())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(Set.of(
                        new MenuItemResponseDto(null, SOUP, 15),
                        new MenuItemResponseDto(null, SANDWICH, 25)
                ));
        assertThat(menuResponseDto).isEqualTo(mapper.toDto(menuService.findByIdWithAllData(1L)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteMenu() throws Exception {
        mockMvc.perform(delete(REST_URL + "/2")).andExpect(status().isNoContent());

        assertThrows(IllegalRequestDataException.class, () -> menuService.findByIdWithAllData(2L));
    }

    @Nested
    class ErrorCasesWithMenu {
        @Test
        void getUnauthorized() throws Exception {
            mockMvc.perform(get(REST_URL + "/1"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @WithUserDetails(value = USER_MAIL)
        void getForbidden() throws Exception {
            mockMvc.perform(delete(REST_URL + "/1")).andExpect(status().isForbidden());
        }

        @Test
        @WithUserDetails(value = ADMIN_MAIL)
        void createUnprocessableMenu() throws Exception {
            MenuRequestDto menuRequestDto = new MenuRequestDto(null, -10, null);

            mockMvc.perform(post(REST_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(JsonUtil.objectToJson(menuRequestDto)))
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.invalid_params").value(MENU_CONSTRAINTS_MATCHER));
        }

        @Test
        @WithUserDetails(value = ADMIN_MAIL)
        void updateUnprocessableMenu() throws Exception {
            MenuRequestDto menuRequestDto = new MenuRequestDto(null, -10, null);

            mockMvc.perform(put(REST_URL + "/3")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(JsonUtil.objectToJson(menuRequestDto)))
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.invalid_params").value(MENU_CONSTRAINTS_MATCHER));
        }

        @Test
        @WithUserDetails(value = ADMIN_MAIL)
        void createDuplicateMenu() throws Exception {
            mockMvc.perform(post(REST_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(JsonUtil.objectToJson(new MenuRequestDto(
                                    MINUS_ONE_DAY,
                                    AISHA_RESTAURANT.getId(),
                                    Set.of(new MenuItemRequestDto(TEA.getId(), 10))))))
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.detail").value("Menu for this restaurant on this date already exists"));
        }

        @Test
        @WithUserDetails(value = ADMIN_MAIL)
        void updateDuplicateMenu() throws Exception {
            mockMvc.perform(put(REST_URL + "/3")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(JsonUtil.objectToJson(new MenuRequestDto(
                                    MINUS_ONE_DAY,
                                    AISHA_RESTAURANT.getId(),
                                    Set.of(new MenuItemRequestDto(TEA.getId(), 10))))))
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.detail").value("Menu for this restaurant on this date already exists"));
        }

        @Test
        @WithUserDetails(value = ADMIN_MAIL)
        void restaurantNotExist() throws Exception {
            mockMvc.perform(post(REST_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(JsonUtil.objectToJson(new MenuRequestDto(
                                    MINUS_ONE_DAY,
                                    100,
                                    Set.of(new MenuItemRequestDto(TEA.getId(), 10))))))
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.detail").value("Wrong id for restaurant"));
        }

        @Test
        @WithUserDetails(value = ADMIN_MAIL)
        void mealNotExist() throws Exception {
            mockMvc.perform(post(REST_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(JsonUtil.objectToJson(new MenuRequestDto(
                                    MINUS_ONE_DAY,
                                    CHERRY_RESTAURANT.getId(),
                                    Set.of(new MenuItemRequestDto(100, 10))))))
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.detail").value("Unable to find ru.jsft.voteforlunch.model.Meal with id 100"));
        }

        @Test
        @WithUserDetails(value = ADMIN_MAIL)
        void wrongMenuItem() throws Exception {
            Locale.setDefault(Locale.ENGLISH);
            mockMvc.perform(post(REST_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(JsonUtil.objectToJson(new MenuRequestDto(
                                    TODAY,
                                    AISHA_RESTAURANT.getId(),
                                    Set.of(new MenuItemRequestDto(TEA.getId(), 0))))))
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.invalid_params").value(MEAL_PRICE_CONSTRAINTS_MATCHER));
        }
    }
}
