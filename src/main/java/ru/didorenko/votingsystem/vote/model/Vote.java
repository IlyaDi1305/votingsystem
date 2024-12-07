package ru.didorenko.votingsystem.vote.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.didorenko.votingsystem.common.model.BaseEntity;
import ru.didorenko.votingsystem.restaurant.model.Restaurant;
import ru.didorenko.votingsystem.user.model.User;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "votes")
@Getter
@Setter
@ToString
public class Vote extends BaseEntity {

    @Column(name = "date")
    private LocalDate date;
    @Column(name = "vote_time")
    private LocalTime voteTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}
