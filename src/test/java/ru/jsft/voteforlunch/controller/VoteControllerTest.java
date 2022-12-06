package ru.jsft.voteforlunch.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import ru.jsft.voteforlunch.AbstractSpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.jsft.voteforlunch.testdata.VoteTestData.VOTES;
import static ru.jsft.voteforlunch.testdata.VoteTestData.VOTE_DTO_MATCHER;

public class VoteControllerTest extends AbstractSpringBootTest {
    private static final String REST_URL = VoteController.REST_URL + '/';

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetVotesForUser() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_DTO_MATCHER.contentJson(VOTES));
    }
}
