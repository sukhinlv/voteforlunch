package ru.jsft.voteforlunch.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.jsft.voteforlunch.AbstractSpringBootTest;
import ru.jsft.voteforlunch.web.controller.VoteController;
import ru.jsft.voteforlunch.web.controller.dto.VoteDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.jsft.voteforlunch.testdata.UserTestData.ADMIN;
import static ru.jsft.voteforlunch.testdata.VoteTestData.*;
import static ru.jsft.voteforlunch.utils.MockAuthorization.userHttpBasic;

public class VoteControllerTest extends AbstractSpringBootTest {
    private static final String REST_URL = VoteController.REST_URL + '/';

    @Test
    void get_Votes_For_User() throws Exception {
        mockMvc.perform(get(REST_URL).with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_DTO_MATCHER.contentJson(VOTES));
    }

    @Test
    void get_Vote() throws Exception {
        mockMvc.perform(get(REST_URL + "/1").with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_DTO_MATCHER.contentJson(VOTE_1L));
    }

    @Test
    void get_Votes_Distribution() throws Exception {
        mockMvc.perform(get(REST_URL +
                        "/distribution?date=" +
                        LocalDate.now(clock).minusDays(2).format(DateTimeFormatter.ISO_DATE))
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_DISTRIBUTION_MATCHER.contentJson(VOTE_DISTRIBUTION));
    }

    @Test
    void save_Vote() throws Exception {
        ResultActions resultActions = mockMvc.perform(post(REST_URL + "/1").with(userHttpBasic(ADMIN)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        VoteDto actual = VOTE_DTO_MATCHER.readFromJson(resultActions);

        assertThat(actual.getVoteDate()).isEqualTo(LocalDate.now(clock));
        assertThat(actual.getRestaurant().getId()).isEqualTo(1L);
    }

    @Test
    void delete_Vote() throws Exception {
        mockMvc.perform(post(REST_URL + "/1").with(userHttpBasic(ADMIN)));

        mockMvc.perform(delete(REST_URL).with(userHttpBasic(ADMIN))).andExpect(status().isNoContent());
    }

    @Test
    void throw_Vote_Not_Found() throws Exception {
        mockMvc.perform(delete(REST_URL)
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Vote of userId = 1 for date = 2022-11-15 not found"));
    }
}
