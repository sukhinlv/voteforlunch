package ru.jsft.voteforlunch.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import ru.jsft.voteforlunch.AbstractSpringBootTest;
import ru.jsft.voteforlunch.web.controller.dto.VoteDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.jsft.voteforlunch.testdata.UserTestData.ADMIN_MAIL;
import static ru.jsft.voteforlunch.testdata.VoteTestData.*;
import static ru.jsft.voteforlunch.web.controller.VoteController.REST_URL;

public class VoteControllerTest extends AbstractSpringBootTest {
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getVotesForUser() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_DTO_MATCHER.contentJson(VOTES));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getVote() throws Exception {
        mockMvc.perform(get(REST_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_DTO_MATCHER.contentJson(VOTE_1L));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getVotesDistribution() throws Exception {
        mockMvc.perform(get(REST_URL +
                        "/distribution?date=" +
                        LocalDate.now(clock).minusDays(2).format(DateTimeFormatter.ISO_DATE)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_DISTRIBUTION_MATCHER.contentJson(VOTE_DISTRIBUTION));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void saveVote() throws Exception {
        ResultActions resultActions = mockMvc.perform(post(REST_URL + "/1"))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        VoteDto actual = VOTE_DTO_MATCHER.readFromJson(resultActions);

        assertThat(actual.getVoteDate()).isEqualTo(LocalDate.now(clock));
        assertThat(actual.getRestaurant().getId()).isEqualTo(1L);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteVote() throws Exception {
        mockMvc.perform(post(REST_URL + "/1"));

        mockMvc.perform(delete(REST_URL)).andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void throwVoteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value("Vote of userId = 1 for date = 2022-11-15 not found"));
    }
}
