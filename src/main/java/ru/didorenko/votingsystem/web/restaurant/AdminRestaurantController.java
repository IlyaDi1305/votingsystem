package ru.didorenko.votingsystem.web.restaurant;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.didorenko.votingsystem.model.Restaurant;
import ru.didorenko.votingsystem.utill.RestaurantUtill;

@Slf4j
@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController extends AbstractRestaurantController {

    public static final String REST_URL = "api/admin/restaurants";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody @Valid String name) {
        log.info("create restaurant with name: {}", name);
        Restaurant restaurant = restaurantService.create(name);
        return ResponseEntity.ok(RestaurantUtill.createTo(restaurant));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid String newName) {
        log.info("update restaurant with id: {} to new name: {}", id, newName);
        Restaurant updated = restaurantService.update(id, newName);
        return ResponseEntity.ok(RestaurantUtill.createTo(updated));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant with id {}", id);
        restaurantService.delete(id);
    }
}