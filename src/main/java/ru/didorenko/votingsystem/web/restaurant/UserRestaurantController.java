package ru.didorenko.votingsystem.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.didorenko.votingsystem.service.RestaurantService;


@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController extends AbstractRestaurantController {

    public static final String REST_URL = "api/user/restaurants";

    public UserRestaurantController(RestaurantService restaurantService) {
        super(restaurantService);
    }
}