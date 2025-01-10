package ru.didorenko.votingsystem.web.restaurant;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.didorenko.votingsystem.to.RestaurantTo;
import ru.didorenko.votingsystem.utill.RestaurantUtil;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController extends AbstractRestaurantController {

    public static final String REST_URL = "/api/admin/restaurants";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RestaurantTo> createWithLocation(@RequestBody @Valid RestaurantTo restaurantTo) {
        log.info("create restaurant with name: {}", restaurantTo.getName());
        RestaurantTo result = restaurantService.create(restaurantTo.getName());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(result);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid RestaurantTo restaurantTo) {
        log.info("update restaurant with id: {} to new name: {}", id, restaurantTo.getName());
        RestaurantUtil.createTo(restaurantService.update(id, restaurantTo.getName()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant with id {}", id);
        restaurantService.delete(id);
    }
}