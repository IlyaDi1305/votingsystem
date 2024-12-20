package ru.didorenko.votingsystem.service;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.didorenko.votingsystem.common.validation.ValidationUtil;
import ru.didorenko.votingsystem.model.MenuItem;
import ru.didorenko.votingsystem.model.Restaurant;
import ru.didorenko.votingsystem.repository.MenuItemRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Cacheable(value = "menuItems", key = "#id + '_' + #restaurantId")
    public MenuItem getExistedByRestaurant(int id, int restaurantId) {
        return repository.getExistedByRestaurant(id, restaurantId);
    }

    @Cacheable(value = "menuItemsByDate", key = "#restaurantId + '_' + #date")
    public List<MenuItem> findAllByRestaurantIdAndDishDate(int restaurantId, LocalDate date) {
        return repository.findAllByRestaurantIdAndDishDate(restaurantId, date);
    }

    @CacheEvict(value = {"menuItems", "menuItemsByDate"}, allEntries = true)
    public void deleteExistedByRestaurant(int id, int restaurantId) {
        repository.deleteExistedByRestaurant(id, restaurantId);
    }

    @CacheEvict(value = {"menuItems", "menuItemsByDate"}, allEntries = true)
    public MenuItem create(MenuItem menuItem, int restaurantId) {
        ValidationUtil.checkNew(menuItem);
        menuItem.setRestaurant(entityManager.getReference(Restaurant.class, restaurantId));
        return repository.save(menuItem);
    }

    @CacheEvict(value = {"menuItems", "menuItemsByDate"}, allEntries = true)
    public void update(MenuItem menuItem, int restaurantId, int id) {
        ValidationUtil.assureIdConsistent(menuItem, id);
        menuItem.setRestaurant(entityManager.getReference(Restaurant.class, restaurantId));
        repository.save(menuItem);
    }
}
