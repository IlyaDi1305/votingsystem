package ru.didorenko.votingsystem.web.restaurant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.didorenko.votingsystem.model.Restaurant;
import ru.didorenko.votingsystem.service.RestaurantService;
import ru.didorenko.votingsystem.to.RestaurantTo;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController {

    private final RestaurantService restaurantService;

    public static final String REST_URL = "/api/user/restaurants";

    @GetMapping("/{id}")
    public RestaurantTo get(@PathVariable int id) {
        log.info("get restaurant with id {}", id);
        return restaurantService.getExisted(id);
    }

    @GetMapping
    public List<RestaurantTo> getAll() {
        log.info("get all restaurants");
        return restaurantService.getAllTo();
    }

    @GetMapping("/by-name")
    public RestaurantTo getByName(@RequestParam String name) {
        log.info("get restaurant by name {}", name);
        return restaurantService.getByName(name);
    }

    @GetMapping("/menuItem/today")
    public List<Restaurant> getAllWithMenuToday() {
        return restaurantService.getAllWithMenuItemByDate(LocalDate.now());
    }
}