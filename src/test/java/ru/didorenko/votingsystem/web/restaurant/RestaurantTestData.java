package ru.didorenko.votingsystem.web.restaurant;

import ru.didorenko.votingsystem.MatcherFactory;
import ru.didorenko.votingsystem.model.MenuItem;
import ru.didorenko.votingsystem.model.Restaurant;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class RestaurantTestData {

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingEqualsComparator(Restaurant.class);

    public static final LocalDate TEST_DATE = LocalDate.now();
    public static final LocalDate TEST_DATE_PLUS_DAY = LocalDate.now().plusDays(1);

    public static final MenuItem BURGER = new MenuItem(1, "Burger", "brotchen, fleisch", 755, TEST_DATE);
    public static final MenuItem COLA = new MenuItem(2, "Cola", "zuker, wasser", 415, TEST_DATE);
    public static final MenuItem PIZZA = new MenuItem(3, "Pizza Salami", "salami", 820, TEST_DATE);
    public static final MenuItem MISOSUP = new MenuItem(4, "Misosup", "wasser, soya", 1290, TEST_DATE);

    public static final MenuItem BURGER_PLUS_DAY = new MenuItem(1, "Burger", "brotchen, fleisch", 755, TEST_DATE_PLUS_DAY);
    public static final MenuItem COLA_PLUS_DAY = new MenuItem(2, "Cola", "zuker, wasser", 415, TEST_DATE_PLUS_DAY);
    public static final MenuItem PIZZA_PLUS_DAY = new MenuItem(3, "Pizza Salami", "salami", 820, TEST_DATE_PLUS_DAY);
    public static final MenuItem MISOSUP_PLUS_DAY = new MenuItem(4, "Misosup", "wasser, soya", 1290, TEST_DATE_PLUS_DAY);

    public static final List<MenuItem> MENU_ITEMS_1 = Arrays.asList(BURGER, COLA);
    public static final List<MenuItem> MENU_ITEMS_2 = List.of(PIZZA);
    public static final List<MenuItem> MENU_ITEMS_3 = List.of(MISOSUP);

    public static final List<MenuItem> MENU_ITEMS_1_PLUS_DAY = Arrays.asList(BURGER, COLA, BURGER_PLUS_DAY, COLA_PLUS_DAY);
    public static final List<MenuItem> MENU_ITEMS_2_PLUS_DAY = Arrays.asList(PIZZA, PIZZA_PLUS_DAY);
    public static final List<MenuItem> MENU_ITEMS_3_PLUS_DAY = Arrays.asList(MISOSUP, MISOSUP_PLUS_DAY);

    public static final Restaurant RESTAURANT_1 = new Restaurant(1, "McDonalds", MENU_ITEMS_1);
    public static final Restaurant RESTAURANT_2 = new Restaurant(2, "Pizzeria Bacco", MENU_ITEMS_2);
    public static final Restaurant RESTAURANT_3 = new Restaurant(3, "Taj im Taunus", MENU_ITEMS_3);

    public static final Restaurant RESTAURANT_1_PLUS_DAY = new Restaurant(1, "McDonalds", MENU_ITEMS_1_PLUS_DAY);
    public static final Restaurant RESTAURANT_2_PLUS_DAY = new Restaurant(2, "Pizzeria Bacco", MENU_ITEMS_2_PLUS_DAY);
    public static final Restaurant RESTAURANT_3_PLUS_DAY = new Restaurant(3, "Taj im Taunus", MENU_ITEMS_3_PLUS_DAY);

    public static final List<Restaurant> RESTAURANTS = Arrays.asList(RESTAURANT_1, RESTAURANT_2, RESTAURANT_3);
    public static final List<Restaurant> RESTAURANTS_PLUS_DAY = Arrays.asList(RESTAURANT_1_PLUS_DAY, RESTAURANT_2_PLUS_DAY, RESTAURANT_3_PLUS_DAY);
}