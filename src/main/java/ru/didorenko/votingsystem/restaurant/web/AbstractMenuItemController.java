package ru.didorenko.votingsystem.restaurant.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.didorenko.votingsystem.restaurant.model.MenuItem;
import ru.didorenko.votingsystem.restaurant.repository.MenuItemRepository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
public abstract class AbstractMenuItemController {

    @Autowired
    protected MenuItemRepository repository;

    @GetMapping("/{id}")
    public MenuItem get(@PathVariable int id, @RequestParam int restaurantId) {
        log.info("get {} for restaurant {}", id, restaurantId);
        return repository.getExistedByRestaurant(id, restaurantId);
    }

    @GetMapping(value = "/by-date", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MenuItem> getMenuByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                        @RequestParam int restaurantId) {
        log.info("get menu for restaurant {} on date {}", restaurantId, date);
        return repository.findAllByRestaurantIdAndDishDate(restaurantId, date);
    }
}