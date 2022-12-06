package ru.jsft.voteforlunch.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.NestedServletException;
import ru.jsft.voteforlunch.AbstractSpringBootTest;
import ru.jsft.voteforlunch.controller.dto.VoteDto;
import ru.jsft.voteforlunch.error.NotFoundException;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.jsft.voteforlunch.testdata.VoteTestData.*;

public class VoteControllerTest extends AbstractSpringBootTest {
    private static final String REST_URL = VoteController.REST_URL + '/';

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Clock clock;

    @Value("${vote.time.constraint}")
    private LocalTime timeConstraint;

    @Test
    void get_Votes_For_User() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_DTO_MATCHER.contentJson(VOTES));
    }

    @Test
    void get_Vote() throws Exception {
        mockMvc.perform(get(REST_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_DTO_MATCHER.contentJson(VOTE_1L));
    }

    @Test
    void get_Votes_Distribution() throws Exception {
        mockMvc.perform(get(REST_URL +
                        "/distribution?date=" +
                        LocalDate.now().minusDays(2).format(DateTimeFormatter.ISO_DATE)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_DISTRIBUTION_MATCHER.contentJson(VOTE_DISTRIBUTION));
    }

    @Test
    void save_Vote() throws Exception {
        ResultActions resultActions = mockMvc.perform(post(REST_URL + "/1"))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        VoteDto actual = VOTE_DTO_MATCHER.readFromJson(resultActions);

        assertThat(actual.getVoteDate()).isEqualTo(LocalDate.now(clock));
        assertThat(actual.getRestaurant().getId()).isEqualTo(1L);
    }

    @Test
    void delete_Vote() throws Exception {
        mockMvc.perform(post(REST_URL + "/1"));

        mockMvc.perform(delete(REST_URL)).andExpect(status().isNoContent());
    }

    @Test
    void throw_Vote_Not_Found() throws Exception {
        NestedServletException parentException = assertThrows(NestedServletException.class,
                () -> mockMvc.perform(delete(REST_URL)));

        Throwable cause = parentException.getCause();
        assertThat(cause)
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Vote of userId = 1 for date = 2022-11-15 not found");
    }
}
