package ru.jsft.voteforlunch.vote;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.jsft.voteforlunch.basemodel.BaseEntity;
import ru.jsft.voteforlunch.menu.Menu;
import ru.jsft.voteforlunch.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vote", uniqueConstraints = {
        @UniqueConstraint(name = "uc_vote_user_id_menu_id", columnNames = {"user_id", "menu_id", "vote_date"})
})
public class Vote extends BaseEntity<Long> {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @NotNull
    @Column(name = "vote_date", nullable = false)
    private LocalDate voteDate;

    @NotNull
    @Column(name = "vote_time", nullable = false)
    private LocalTime voteTime;

    @PreUpdate
    public void preUpdate() {
        // TODO: implement time check
    }
}
