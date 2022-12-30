package ru.jsft.voteforlunch.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import ru.jsft.voteforlunch.AbstractSpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.jsft.voteforlunch.testdata.MenuTestData.*;
import static ru.jsft.voteforlunch.testdata.UserTestData.USER_MAIL;
import static ru.jsft.voteforlunch.web.controller.MenuController.REST_URL;

public class MenuControllerTest extends AbstractSpringBootTest {
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
    @WithUserDetails(value = USER_MAIL)
    void getListOfMenus() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_LIST_DTO_MATCHER.contentJson(MENUS_LIST));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getListOfMenusOnDate() throws Exception {
        mockMvc.perform(get(REST_URL + "/on-date?date=" + MINUS_TWO_DAYS))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_LIST_DTO_MATCHER.contentJson(MINUS_TWO_DAYS_MENUS_LIST));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getMenu() throws Exception {
        mockMvc.perform(get(REST_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_RESPONSE_DTO_MATCHER.contentJson(MENU_FOR_CHERRY1));
    }

/*
* getListOfMenus
* getListOfMenusOnDate
* get
* create
* update
* delete
*
* ошибки
* - новое меню с ошибками
* - обновление меню с ошибками
* - дубликат меню на дату
* - указан несуществующий ресторан
* - указана ссылка на несуществующую еду
* - указана неправильная цена еды
* - пустой список еды
* */
}
