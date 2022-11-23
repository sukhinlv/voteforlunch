package ru.jsft.voteforlunch.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uc_vote_user_id_vote_date", columnNames = {"user_id", "vote_date"})
})
@NamedNativeQuery(
        name = "get_votes_distribution_on_date",
        query =
                "SELECT DISTINCT r.id AS restaurantId, r.name AS restaurantName, v.vote_count AS voteCount " +
                        "FROM restaurant AS r " +
                        "LEFT JOIN (" +
                        "   SELECT restaurant_id, COUNT(id) AS vote_count  " +
                        "   FROM vote " +
                        "   WHERE vote_date = :distDate " +
                        "   GROUP BY restaurant_id" +
                        ") AS v " +
                        "   ON r.id = v.restaurant_id " +
                        "ORDER BY v.vote_count DESC",
        resultSetMapping = "vote_distribution"
)
@SqlResultSetMapping(
        name = "vote_distribution",
        classes = @ConstructorResult(
                targetClass = VoteDistribution.class,
                columns = {
                        @ColumnResult(name = "restaurantId", type = Long.class),
                        @ColumnResult(name = "restaurantName", type = String.class),
                        @ColumnResult(name = "voteCount", type = Long.class)
                }
        )
)
@ToString
public class Vote extends AbstractEntity {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Restaurant restaurant;

    @NotNull
    @Column(name = "vote_date", nullable = false)
    private LocalDate voteDate;

    @NotNull
    @Column(name = "vote_time", nullable = false)
    private LocalTime voteTime;
}
