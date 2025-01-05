package ru.didorenko.votingsystem.utill;

import ru.didorenko.votingsystem.model.MenuItem;

public class MenuItemUtil {

    public static MenuItem setWithoutRestaurant(MenuItem menuItem) {
        menuItem.setName(menuItem.getName());
        menuItem.setPrice(menuItem.getPrice());
        menuItem.setMenuItemDate(menuItem.getMenuItemDate());
        return menuItem;
    }
}