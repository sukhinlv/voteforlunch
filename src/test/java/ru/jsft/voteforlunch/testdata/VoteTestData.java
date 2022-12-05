package ru.jsft.voteforlunch.testdata;

import ru.jsft.voteforlunch.model.User;
import ru.jsft.voteforlunch.model.Vote;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ru.jsft.voteforlunch.testdata.RestaurantTestData.AISHA_RESTAURANT;
import static ru.jsft.voteforlunch.testdata.RestaurantTestData.CHERRY_RESTAURANT;
import static ru.jsft.voteforlunch.testdata.UserTestData.USER_LIST;

public class VoteTestData {
    public static List<Vote> VOTES = new ArrayList<>();

    static {
        Random rnd = new Random();
        for (
                User usr : USER_LIST) {
            VOTES.add(new Vote(
                    usr,
                    rnd.nextBoolean() ? AISHA_RESTAURANT : CHERRY_RESTAURANT,
                    LocalDate.now(),
                    LocalTime.of(rnd.nextInt(6, 11), rnd.nextInt(0, 60))));
        }
    }
}
