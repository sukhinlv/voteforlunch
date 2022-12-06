package ru.jsft.voteforlunch.testdata;

import ru.jsft.voteforlunch.controller.dto.VoteDto;
import ru.jsft.voteforlunch.model.VoteDistribution;
import ru.jsft.voteforlunch.utils.MatcherFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.jsft.voteforlunch.testdata.RestaurantTestData.AISHA_RESTAURANT;
import static ru.jsft.voteforlunch.testdata.RestaurantTestData.CHERRY_RESTAURANT;

public class VoteTestData {
    public static MatcherFactory.Matcher<VoteDto> VOTE_DTO_MATCHER = MatcherFactory.usingEqualsComparator(VoteDto.class);
    public static MatcherFactory.Matcher<VoteDistribution> VOTE_DISTRIBUTION_MATCHER = MatcherFactory.usingEqualsComparator(VoteDistribution.class);

    public static VoteDto VOTE_1L = new VoteDto(1L, AISHA_RESTAURANT, LocalDate.now().minusDays(2), LocalTime.of(9, 30));
    public static VoteDto VOTE_3L = new VoteDto(3L, CHERRY_RESTAURANT, LocalDate.now().minusDays(1), LocalTime.of(9, 30));
    public static List<VoteDto> VOTES = List.of(VOTE_3L, VOTE_1L);

    public static List<VoteDistribution> VOTE_DISTRIBUTION = List.of(
            new VoteDistribution(2L, "Aisha", 2L),
            new VoteDistribution(1L, "Cherry", 0L)
    );
}
