package ru.didorenko.votingsystem.restaurant.web.AdminController;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.didorenko.votingsystem.restaurant.model.MenuItem;
import ru.didorenko.votingsystem.restaurant.model.Restaurant;
import ru.didorenko.votingsystem.restaurant.web.AbstractMenuItemController;

import java.net.URI;

import static ru.didorenko.votingsystem.common.validation.ValidationUtil.assureIdConsistent;
import static ru.didorenko.votingsystem.common.validation.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequestMapping(value = AdminMenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuItemController extends AbstractMenuItemController {

    @Autowired
    private EntityManager entityManager;

    static final String REST_URL = AdminRestaurantController.REST_URL + "/{restaurantId}/menuItems";

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = {"menuItemCache", "menuItemsByDateCache"}, allEntries = true)
    public void delete(@PathVariable int id, @RequestParam int restaurantId) {
        log.info("delete {} for restaurant {}", id, restaurantId);
        repository.deleteExistedByRestaurant(id, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = {"menuItemCache", "menuItemsByDateCache"}, allEntries = true)
    public ResponseEntity<MenuItem> createWithLocation(@Valid @RequestBody MenuItem menuItem, @PathVariable int restaurantId) {
        log.info("create {}", menuItem);
        checkNew(menuItem);
        menuItem.setRestaurant(entityManager.getReference(Restaurant.class, restaurantId));
        MenuItem created = repository.save(menuItem);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = {"menuItemCache", "menuItemsByDateCache"}, allEntries = true)
    public void update(@Valid @RequestBody MenuItem menuItem, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("update {} with id={} for restaurant {}", menuItem, id, restaurantId);
        assureIdConsistent(menuItem, id);
        menuItem.setRestaurant(entityManager.getReference(Restaurant.class, restaurantId));
        repository.save(menuItem);
    }
}