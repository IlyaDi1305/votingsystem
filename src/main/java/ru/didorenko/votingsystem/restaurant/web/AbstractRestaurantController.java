package ru.didorenko.votingsystem.restaurant.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import ru.didorenko.votingsystem.restaurant.model.Restaurant;
import ru.didorenko.votingsystem.restaurant.repository.RestaurantRepository;

import java.util.List;

@Slf4j
public abstract class AbstractRestaurantController {

    @Autowired
    protected RestaurantRepository repository;

    @GetMapping("/{id}")
    @Cacheable(value = "restaurantCache", key = "#id")
    public Restaurant get(@PathVariable int id) {
        log.info("get {}", id);
        return repository.getExisted(id);
    }

    @GetMapping
    @Cacheable(value = "restaurantsCache", key = "'allRestaurants'")
    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.findAll();
    }

    @GetMapping("/by-name")
    @Cacheable(value = "restaurantCache", key = "#name")
    public Restaurant getByName(@RequestParam String name) {
        log.info("getByName {}", name);
        return repository.getExistedByName(name);
    }
}
