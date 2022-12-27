package ru.jsft.voteforlunch.testdata;

import ru.jsft.voteforlunch.model.VoteDistribution;
import ru.jsft.voteforlunch.utils.MatcherFactory;
import ru.jsft.voteforlunch.web.controller.dto.VoteDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.jsft.voteforlunch.testdata.RestaurantTestData.AISHA_RESTAURANT;
import static ru.jsft.voteforlunch.testdata.RestaurantTestData.CHERRY_RESTAURANT;

public class VoteTestData {
    public static MatcherFactory.Matcher<VoteDto> VOTE_DTO_MATCHER = MatcherFactory.usingEqualsComparator(VoteDto.class);
    public static MatcherFactory.Matcher<VoteDistribution> VOTE_DISTRIBUTION_MATCHER = MatcherFactory.usingEqualsComparator(VoteDistribution.class);

    public static VoteDto VOTE_1L = new VoteDto(AISHA_RESTAURANT, LocalDate.of(2022, 11, 13), LocalTime.of(9, 30));
    public static VoteDto VOTE_3L = new VoteDto(CHERRY_RESTAURANT, LocalDate.of(2022, 11, 14), LocalTime.of(9, 30));

    static {
        VOTE_1L.setId(1L);
        VOTE_3L.setId(3L);
    }

    public static List<VoteDto> VOTES = List.of(VOTE_3L, VOTE_1L);

    public static List<VoteDistribution> VOTE_DISTRIBUTION = List.of(
            new VoteDistribution(2L, "Aisha", 2L),
            new VoteDistribution(1L, "Cherry", 0L)
    );
}
