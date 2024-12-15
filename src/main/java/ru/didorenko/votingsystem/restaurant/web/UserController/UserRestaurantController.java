package ru.didorenko.votingsystem.restaurant.web.UserController;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.didorenko.votingsystem.restaurant.web.AbstractRestaurantController;


@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "api/user/restaurants";
}