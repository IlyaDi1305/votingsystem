package ru.didorenko.votingsystem.web.restaurant;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.didorenko.votingsystem.model.Restaurant;
import ru.didorenko.votingsystem.to.RestaurantTo;
import ru.didorenko.votingsystem.utill.RestaurantUtill;

@Slf4j
@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController extends AbstractRestaurantController {

    public static final String REST_URL = "api/admin/restaurants";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody @Valid RestaurantTo restaurantTo) {
        log.info("create restaurant with name: {}", restaurantTo.getName());
        RestaurantTo result = RestaurantUtill.createTo(restaurantService.create(restaurantTo.getName()));
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid RestaurantTo restaurantTo) {
        log.info("update restaurant with id: {} to new name: {}", id, restaurantTo.getName());
        Restaurant updated = restaurantService.update(id, restaurantTo.getName());
        RestaurantTo result = RestaurantUtill.createTo(updated);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant with id {}", id);
        restaurantService.delete(id);
    }
}