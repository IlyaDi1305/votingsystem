package ru.didorenko.votingsystem.web.menuitem;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.didorenko.votingsystem.common.validation.ValidationUtil;
import ru.didorenko.votingsystem.model.MenuItem;
import ru.didorenko.votingsystem.service.MenuItemService;
import ru.didorenko.votingsystem.web.restaurant.AdminRestaurantController;
import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = AdminMenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuItemController {

    private final MenuItemService menuItemService;

    static final String REST_URL = AdminRestaurantController.REST_URL;

    @GetMapping("/menuItems/{id}")
    public MenuItem get(@PathVariable int id) {
        log.info("get menu item id: {}" , id);
        return menuItemService.getByIdMenuItems(id);
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
                .path(REST_URL + "/menuItem/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restaurantId}/menuItems/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuItem menuItem, @PathVariable int restaurantId, @PathVariable int id) {
        ValidationUtil.assureIdConsistent(menuItem, id);
        log.info("update menu item id {} for restaurant {}", id, restaurantId);
        menuItemService.update(menuItem, restaurantId);
    }
}