package ru.didorenko.votingsystem.restaurant.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.didorenko.votingsystem.restaurant.model.Restaurant;
import ru.didorenko.votingsystem.restaurant.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/restaurants")
@AllArgsConstructor
public class AdminRestaurantController {

    private RestaurantService restaurantService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable("id") Integer restaurantId) {
        if (restaurantId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Restaurant restaurant = restaurantService.getById(restaurantId);
        if (restaurant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> saveRestaurant(@RequestBody @Valid Restaurant restaurant) {
        HttpHeaders headers = new HttpHeaders();
        if (restaurant == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.restaurantService.save(restaurant);
        return new ResponseEntity<>(restaurant, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody @Valid Restaurant restaurant) {
        HttpHeaders headers = new HttpHeaders();
        if (restaurant == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.restaurantService.save(restaurant);
        return new ResponseEntity<>(restaurant, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> deleteRestaurant(@PathVariable("id") Integer id) {
        Restaurant restaurant = this.restaurantService.getById(id);
        if (restaurant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.restaurantService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = this.restaurantService.getAll();
        if (restaurants.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }
}