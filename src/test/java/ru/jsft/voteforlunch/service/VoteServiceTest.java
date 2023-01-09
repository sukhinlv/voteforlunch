package ru.jsft.voteforlunch.service;

import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.jsft.voteforlunch.error.IllegalRequestDataException;
import ru.jsft.voteforlunch.error.VoteTimeConstraintException;
import ru.jsft.voteforlunch.model.Restaurant;
import ru.jsft.voteforlunch.model.User;
import ru.jsft.voteforlunch.model.Vote;
import ru.jsft.voteforlunch.model.VoteDistribution;
import ru.jsft.voteforlunch.repository.RestaurantRepository;
import ru.jsft.voteforlunch.repository.UserRepository;
import ru.jsft.voteforlunch.repository.VoteRepository;

import java.time.*;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

class VoteServiceTest {
    private static final ZonedDateTime NOW = ZonedDateTime.of(
            2022, 11, 15, 9, 30, 0, 0,
            ZoneId.of("GMT"));

    private static final LocalTime TIME_CONSTRAINT = LocalTime.of(11, 0);

    public static final ZonedDateTime NOW_AFTER_TIME_CONSTRAINT = ZonedDateTime.of(
            NOW.getYear(), NOW.getMonthValue(), NOW.getDayOfMonth(),
            TIME_CONSTRAINT.getHour(), TIME_CONSTRAINT.getMinute(), TIME_CONSTRAINT.getSecond(),
            TIME_CONSTRAINT.getNano() + 1,
            NOW.getZone());

    private VoteService underTest;

    @Mock
    private VoteRepository voteRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private Clock clock;

    @Captor
    ArgumentCaptor<Vote> voteCaptor;
    @Captor
    ArgumentCaptor<Long> idCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(clock.instant()).thenReturn(NOW.toInstant());
        underTest = new VoteService(voteRepository, userRepository, restaurantRepository, clock, TIME_CONSTRAINT);
    }

    @Test
    void getVotesDistributionOnDate() {
        List<VoteDistribution> votesList = List.of(
                new VoteDistribution(1L, "Restaurant One", 65L),
                new VoteDistribution(2L, "Restaurant Two", 35L));
        LocalDate date = LocalDate.of(2022, 12, 15);

        when(voteRepository.getVotesDistributionOnDate(date)).thenReturn(votesList);

        assertThat(underTest.getVotesDistributionOnDate(date)).usingRecursiveComparison().isEqualTo(votesList);
    }

    @Nested
    class FindVotes {
        @Test
        void findVotes() {
            Vote vote = Instancio.create(Vote.class);
            Long id = vote.getId();
            Long userId = vote.getUser().getId();
            when(voteRepository.findByIdAndUserId(id, userId)).thenReturn(Optional.of(vote));

            assertThat(underTest.find(id, userId)).usingRecursiveComparison().isEqualTo(vote);
        }

        @Test
        void findAllUserVotes() {
            User user1 = Instancio.create(User.class);
            User user2 = Instancio.create(User.class);
            Vote vote1 = Instancio.create(Vote.class);
            Vote vote2 = Instancio.create(Vote.class);
            Vote vote3 = Instancio.create(Vote.class);
            vote1.setUser(user1);
            vote2.setUser(user2);
            vote3.setUser(user1);
            when(voteRepository.findAllForUser(user1.getId())).thenReturn(List.of(vote1, vote3));

            assertThat(underTest.findAllForUser(user1.getId())).usingRecursiveComparison().isEqualTo(List.of(vote1, vote3));
        }
    }

    @Nested
    class SaveVotes {
        @Test
        void saveNewVote() {
            User user = Instancio.create(User.class);
            User userIdOnly = new User();
            userIdOnly.setId(user.getId());

            Restaurant restaurant = Instancio.create(Restaurant.class);
            Restaurant restaurantIdOnly = new Restaurant();
            restaurantIdOnly.setId(restaurant.getId());

            when(voteRepository.findByVoteDateAndUserId(LocalDate.now(clock), user.getId())).thenReturn(Optional.empty());
            when(restaurantRepository.getReferenceById(restaurant.getId())).thenReturn(restaurantIdOnly);
            when(userRepository.getReferenceById(user.getId())).thenReturn(userIdOnly);

            underTest.saveAndReturnWithDetails(restaurant.getId(), user.getId());

            then(voteRepository).should().save(voteCaptor.capture());

            Vote vote = new Vote();
            vote.setRestaurant(restaurant);
            vote.setUser(user);
            vote.setVoteDate(LocalDate.now(clock));
            vote.setVoteTime(LocalTime.now(clock));
            Vote voteCaptorValue = voteCaptor.getValue();
            assertThat(voteCaptorValue.getId()).isEqualTo(vote.getId());
            assertThat(voteCaptorValue.getVoteTime()).isEqualTo(vote.getVoteTime());
            assertThat(voteCaptorValue.getVoteDate()).isEqualTo(vote.getVoteDate());
            assertThat(voteCaptorValue.getUser().getId()).isEqualTo(vote.getUser().getId());
            assertThat(voteCaptorValue.getRestaurant().getId()).isEqualTo(vote.getRestaurant().getId());
        }

        @Test
        void updateVote() {
            User user = Instancio.create(User.class);

            Restaurant restaurant = Instancio.create(Restaurant.class);
            Vote vote = new Vote();
            vote.setId(1L);
            vote.setRestaurant(restaurant);
            vote.setUser(user);
            vote.setVoteDate(LocalDate.now(clock));
            vote.setVoteTime(LocalTime.now(clock).minusMinutes(30));

            Restaurant updatedRestaurant = Instancio.create(Restaurant.class);
            Restaurant updatedRestaurantIdOnly = new Restaurant();
            updatedRestaurantIdOnly.setId(updatedRestaurant.getId());

            when(voteRepository.findByVoteDateAndUserId(LocalDate.now(clock), user.getId())).thenReturn(Optional.of(vote));
            when(restaurantRepository.getReferenceById(updatedRestaurant.getId())).thenReturn(updatedRestaurantIdOnly);

            underTest.saveAndReturnWithDetails(updatedRestaurant.getId(), user.getId());

            then(voteRepository).should().save(voteCaptor.capture());

            Vote updatedVote = new Vote();
            updatedVote.setId(1L);
            updatedVote.setRestaurant(updatedRestaurant);
            updatedVote.setUser(user);
            updatedVote.setVoteDate(LocalDate.now(clock));
            updatedVote.setVoteTime(LocalTime.now(clock));
            Vote voteCaptorValue = voteCaptor.getValue();
            assertThat(voteCaptorValue.getId()).isEqualTo(vote.getId());
            assertThat(voteCaptorValue.getVoteTime()).isEqualTo(vote.getVoteTime());
            assertThat(voteCaptorValue.getVoteDate()).isEqualTo(vote.getVoteDate());
            assertThat(voteCaptorValue.getUser().getId()).isEqualTo(vote.getUser().getId());
            assertThat(voteCaptorValue.getRestaurant().getId()).isEqualTo(vote.getRestaurant().getId());
        }
    }

    @Nested
    class DeleteVotes {
        @Test
        void throwWhenDeleteAbsentVote() {
            long userId = 1L;
            when(voteRepository.findByVoteDateAndUserId(LocalDate.now(clock), userId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> underTest.delete(userId))
                    .isInstanceOf(IllegalRequestDataException.class)
                    .hasMessageContaining(String.format("Vote of userId = %s for date = %s not found", userId, LocalDate.now(clock)));
        }

        @Test
        void throwWhenDeleteWithTimeConstraintViolation() {
            when(clock.instant()).thenReturn(NOW_AFTER_TIME_CONSTRAINT.toInstant());

            assertThatThrownBy(() -> underTest.delete(1L))
                    .isInstanceOf(VoteTimeConstraintException.class)
                    .hasMessageContaining(String.format("You can only change your vote until %s", TIME_CONSTRAINT));
        }
    }
}
