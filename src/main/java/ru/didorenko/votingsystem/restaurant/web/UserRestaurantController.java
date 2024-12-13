package ru.didorenko.votingsystem.restaurant.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.didorenko.votingsystem.restaurant.model.Restaurant;
import ru.didorenko.votingsystem.restaurant.repository.RestaurantRepository;

import java.util.List;
@Slf4j
@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController {

    @Autowired
    protected RestaurantRepository repository;

    static final String REST_URL = "api/user/restaurants";

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get {}", id);
        return repository.getExisted(id);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.findAll();
    }

    @GetMapping("/by-name")
    public Restaurant getByName(@RequestParam String name) {
        log.info("getByEmail {}", name);
        return repository.getExistedByName(name);
    }
}
