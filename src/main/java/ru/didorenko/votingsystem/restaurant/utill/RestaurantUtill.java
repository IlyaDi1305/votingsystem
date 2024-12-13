package ru.didorenko.votingsystem.restaurant.utill;

import ru.didorenko.votingsystem.restaurant.model.Restaurant;

public class RestaurantUtill {

    public static Restaurant createNewWithName(String name) {
        return new Restaurant(null, name);
    }

    public static Restaurant updateWithName(Restaurant restaurant, String name) {
        restaurant.setName(name);
        return restaurant;
    }
}
