package ru.jsft.voteforlunch.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.jsft.voteforlunch.AbstractSpringBootTest;
import ru.jsft.voteforlunch.model.Meal;
import ru.jsft.voteforlunch.web.json.JsonUtil;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class MealControllerTest extends AbstractSpringBootTest {
    private static final String REST_URL = MealController.REST_URL + '/';

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateMeal() throws Exception {
        Meal meal = new Meal("Some test meal");

        MvcResult mvcResult = mockMvc.perform(post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JsonUtil.objectToJson(meal))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        Meal responseMeal = JsonUtil.jsonToObject(mvcResult.getResponse().getContentAsString(), Meal.class);
        assertThat(responseMeal).usingRecursiveComparison().ignoringFields("id").isEqualTo(meal);
    }
}
