package ru.didorenko.votingsystem.utill;

import ru.didorenko.votingsystem.model.Restaurant;
import ru.didorenko.votingsystem.to.RestaurantTo;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantUtill {

    public static Restaurant createNewWithName(String name) {
        return new Restaurant(null, name);
    }

    public static Restaurant updateWithName(Restaurant restaurant, String name) {
        restaurant.setName(name);
        return restaurant;
    }

    public static RestaurantTo createTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.id(), restaurant.getName());
    }

    public static List<RestaurantTo> createToList(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantUtill::createTo)
                .collect(Collectors.toList());
    }
}