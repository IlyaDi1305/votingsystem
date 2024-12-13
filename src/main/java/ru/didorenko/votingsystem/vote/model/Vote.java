package ru.didorenko.votingsystem.vote.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.didorenko.votingsystem.common.model.BaseEntity;
import ru.didorenko.votingsystem.restaurant.model.Restaurant;
import ru.didorenko.votingsystem.user.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "vote")
@Getter
@Setter
@NoArgsConstructor
public class Vote extends BaseEntity {

    @Column(name = "date")
    @NotNull
    private LocalDate date;

    @Column(name = "time")
    @NotNull
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Vote(Integer id, LocalDate date, LocalTime time, User user, Restaurant restaurant) {
        super(id);
        this.date = date;
        this.time = time;
        this.user = user;
        this.restaurant = restaurant;
    }

    public Vote(User user, Restaurant restaurant) {
        super(null);
        this.date = LocalDate.now();
        this.time = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        this.user = user;
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", time=" + time +
                ", date=" + date +
                '}';
    }
}