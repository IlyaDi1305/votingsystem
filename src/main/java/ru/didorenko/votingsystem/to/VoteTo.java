package ru.didorenko.votingsystem.to;

import lombok.*;
import ru.didorenko.votingsystem.common.to.BaseTo;
import java.time.LocalDate;

@Getter
@Setter
//@EqualsAndHashCode(callSuper = true)
@ToString
@NoArgsConstructor
public class VoteTo extends BaseTo {

    private LocalDate date;

    private Integer restaurantId;

    private Integer userId;

    public VoteTo(Integer id, LocalDate date, Integer restaurantId, Integer userId) {
        super(id);
        this.date = date;
        this.restaurantId = restaurantId;
        this.userId = userId;
    }
}