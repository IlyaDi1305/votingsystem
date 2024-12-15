package ru.didorenko.votingsystem.restaurant.web.UserController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.didorenko.votingsystem.restaurant.web.AbstractMenuItemController;

@Slf4j
@RestController
@RequestMapping(value = UserMenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserMenuItemController extends AbstractMenuItemController {

    static final String REST_URL = UserRestaurantController.REST_URL + "/{restaurantId}/menuItems";
}