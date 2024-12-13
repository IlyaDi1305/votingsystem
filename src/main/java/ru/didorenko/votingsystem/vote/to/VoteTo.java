package ru.didorenko.votingsystem.vote.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.didorenko.votingsystem.common.to.BaseTo;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class VoteTo extends BaseTo {

    private LocalDate date;

    private Integer restaurantId;

    public VoteTo(Integer id, LocalDate date, Integer restaurantId) {
        super(id);
        this.date = date;
        this.restaurantId = restaurantId;
    }
}
