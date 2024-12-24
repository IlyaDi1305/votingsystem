package ru.didorenko.votingsystem.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.didorenko.votingsystem.common.to.NamedTo;

@EqualsAndHashCode(callSuper = true)
@Value
@ToString
public class RestaurantTo extends NamedTo {

    public RestaurantTo(Integer id, String name) {
        super(id, name);
    }
}
