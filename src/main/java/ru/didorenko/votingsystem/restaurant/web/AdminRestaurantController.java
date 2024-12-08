package ru.didorenko.votingsystem.restaurant.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.didorenko.votingsystem.restaurant.model.Restaurant;
import ru.didorenko.votingsystem.restaurant.repository.RestaurantRepository;
import ru.didorenko.votingsystem.restaurant.to.RestaurantTo;
import ru.didorenko.votingsystem.restaurant.utill.RestaurantUtill;

import java.net.URI;
import java.util.List;

import static ru.didorenko.votingsystem.common.validation.ValidationUtil.assureIdConsistent;
import static ru.didorenko.votingsystem.common.validation.ValidationUtil.checkNew;


@Slf4j
@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    @Autowired
    protected RestaurantRepository repository;

    static final String REST_URL = "api/admin/restaurants";

    @GetMapping("/{id}")
    public Restaurant get(int id) {
        log.info("get {}", id);
        return repository.getExisted(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody RestaurantTo restaurantTo) {
        log.info("create {}", restaurantTo);
        checkNew(restaurantTo);
        Restaurant created = repository.save(RestaurantUtill.createNewFromTo(restaurantTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid RestaurantTo restaurantTo, Restaurant restaurant) {
        log.info("update {} with id={}", restaurant, restaurant.getId());
        assureIdConsistent(restaurantTo, restaurant.id());
        repository.save(RestaurantUtill.updateFromTo(restaurant, restaurantTo));
    }

    @GetMapping("/by-name")
    public Restaurant getByName(@RequestParam String name) {
        log.info("getByEmail {}", name);
        return repository.getExistedByName(name);
    }
}
