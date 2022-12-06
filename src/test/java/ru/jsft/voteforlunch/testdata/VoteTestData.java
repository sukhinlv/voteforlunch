package ru.jsft.voteforlunch.testdata;

import ru.jsft.voteforlunch.controller.dto.VoteDto;
import ru.jsft.voteforlunch.utils.MatcherFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ru.jsft.voteforlunch.testdata.RestaurantTestData.AISHA_RESTAURANT;
import static ru.jsft.voteforlunch.testdata.RestaurantTestData.CHERRY_RESTAURANT;

public class VoteTestData {
    public static MatcherFactory.Matcher<VoteDto> VOTE_DTO_MATCHER = MatcherFactory.usingEqualsComparator(VoteDto.class);
    public static List<VoteDto> VOTES = new ArrayList<>();

    static {
        VOTES.addAll(List.of(
                new VoteDto(3L, CHERRY_RESTAURANT, LocalDate.now(), LocalTime.of(9, 30)),
                new VoteDto(1L, AISHA_RESTAURANT, LocalDate.now().minusDays(1), LocalTime.of(9, 30))
        ));
    }
}
