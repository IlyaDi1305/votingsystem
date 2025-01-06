package ru.didorenko.votingsystem.web.menuitem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.didorenko.votingsystem.model.MenuItem;
import ru.didorenko.votingsystem.service.MenuItemService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractMenuItemController {

    protected final MenuItemService menuItemService;

    @GetMapping("/{id}")
    public MenuItem get(@PathVariable int id, @RequestParam int restaurantId) {
        log.info("get menu item {} for restaurant {}", id, restaurantId);
        return menuItemService.getExistedByRestaurant(id, restaurantId);
    }

    @GetMapping("/by-date/{date}")
    public List<MenuItem> getMenuByDate(@PathVariable int restaurantId,
                                        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get menu for restaurant {} on date {}", restaurantId, date);
        return menuItemService.findAllByRestaurantIdAndDishDate(restaurantId, date);
    }

    @GetMapping("/all-by-date/{date}")
    public List<MenuItem> getAllMenuByDate(@PathVariable
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get All menu by date {}", date);
        return menuItemService.findAllByDate(date);
    }
}