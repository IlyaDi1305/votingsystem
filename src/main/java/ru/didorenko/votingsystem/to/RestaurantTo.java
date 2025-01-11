package ru.didorenko.votingsystem.to;

import lombok.*;
import ru.didorenko.votingsystem.common.to.NamedTo;

@EqualsAndHashCode(callSuper = true)
@Value
@ToString
@Getter
@Setter
public class RestaurantTo extends NamedTo {

    public RestaurantTo(Integer id, String name) {
        super(id, name);
    }
}