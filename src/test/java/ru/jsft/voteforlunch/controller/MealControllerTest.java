package ru.jsft.voteforlunch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.jsft.voteforlunch.model.Meal;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.jsft.voteforlunch.utils.ObjectUtils.objectToJson;

@SpringBootTest
@AutoConfigureMockMvc
class MealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void itShouldCreateMeal() throws Exception {
        Meal meal = new Meal("Some test meal");

        ResultActions performCreateNewMeal = mockMvc.perform(post("/api/v1/meals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectToJson(meal))));

        performCreateNewMeal.andExpect(status().isCreated());

        String jsonResponse = performCreateNewMeal.andReturn().getResponse().getContentAsString();
        Meal responseMeal = new ObjectMapper().readValue(jsonResponse, Meal.class);
        assertThat(responseMeal).usingRecursiveComparison().ignoringFields("id").isEqualTo(meal);
    }
}
