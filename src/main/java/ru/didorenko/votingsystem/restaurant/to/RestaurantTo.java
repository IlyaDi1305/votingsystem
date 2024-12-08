package ru.didorenko.votingsystem.restaurant.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.didorenko.votingsystem.common.to.NamedTo;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo {

    public RestaurantTo(Integer id, String name) {
        super(id, name);
    }
}
