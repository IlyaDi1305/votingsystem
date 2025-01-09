package ru.didorenko.votingsystem.web.menuitem;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.didorenko.votingsystem.common.validation.ValidationUtil;
import ru.didorenko.votingsystem.model.MenuItem;
import ru.didorenko.votingsystem.web.restaurant.AdminRestaurantController;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = AdminMenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuItemController extends AbstractMenuItemController {

    static final String REST_URL = AdminRestaurantController.REST_URL;

    @GetMapping("/menuItems/all-by-date/{date}")
    public List<MenuItem> getAllMenuByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get All menu by date {}", date);
        return menuItemService.getAllByDate(date);
    }

    @GetMapping("/{restaurantId}/menuItem/by-date/{date}")
    public List<MenuItem> getALlByRestaurantIdAndDate(@PathVariable int restaurantId,
                                           @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get menu for restaurant {} on date {}", restaurantId, date);
        return menuItemService.getAllByRestaurantIdAndMenuItemDate(restaurantId, date);
    }

    @DeleteMapping("/menuItems/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete menu item {} ", id);
        menuItemService.delete(id);
    }

    @PostMapping(value = "/{restaurantId}/menuItems", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> createWithLocation(@Valid @RequestBody MenuItem menuItem, @PathVariable int restaurantId) {
        log.info("create {}", menuItem);
        ValidationUtil.checkNew(menuItem);
        MenuItem created = menuItemService.create(menuItem, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restaurantId}/menuItems/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuItem menuItem, @PathVariable int restaurantId) {
        ValidationUtil.assureIdConsistent(menuItem, restaurantId);
        log.info("update menu item {} for restaurant {}", menuItem, restaurantId);
        menuItemService.update(menuItem, restaurantId);
    }
}