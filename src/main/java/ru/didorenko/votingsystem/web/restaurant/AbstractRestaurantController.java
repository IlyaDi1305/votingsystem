package ru.didorenko.votingsystem.web.restaurant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.didorenko.votingsystem.service.RestaurantService;
import ru.didorenko.votingsystem.to.RestaurantTo;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractRestaurantController {

    protected final RestaurantService restaurantService;

    @GetMapping("/{id}")
    public RestaurantTo get(@PathVariable int id) {
        log.info("get restaurant with id {}", id);
        return restaurantService.getExisted(id);
    }

    @GetMapping
    public List<RestaurantTo> getAll() {
        log.info("get all restaurants");
        return restaurantService.getAll();
    }

    @GetMapping("/by-name")
    public RestaurantTo getByName(@RequestParam String name) {
        log.info("get restaurant by name {}", name);
        return restaurantService.getByName(name);
    }
}