package ru.didorenko.votingsystem.web.menuitem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.didorenko.votingsystem.model.MenuItem;
import ru.didorenko.votingsystem.service.MenuItemService;
import java.time.LocalDate;
import java.util.List;

@Slf4j
public abstract class AbstractMenuItemController {

    @Autowired
    protected MenuItemService menuItemService;

    @GetMapping("/menuItem/{id}")
    public MenuItem get(@PathVariable int id) {
        log.info("get menu item id: {}" , id);
        return menuItemService.getByIdMenuItems(id);
    }

    @GetMapping("menuItem/allToday")
    public List<MenuItem> getAllToday() {
        log.info("get All today");
        return menuItemService.getAllByDate(LocalDate.now());
    }

    @GetMapping("/{restaurantId}/menuItem/today")
    public List<MenuItem> getAllByRestaurantIdToday(@PathVariable int restaurantId) {
        log.info("get menu for restaurant {} today", restaurantId);
        return menuItemService.getAllByRestaurantIdAndMenuItemDate(restaurantId, LocalDate.now());
    }
}