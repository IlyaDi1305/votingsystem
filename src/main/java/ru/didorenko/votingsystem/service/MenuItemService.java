package ru.didorenko.votingsystem.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.didorenko.votingsystem.model.MenuItem;
import ru.didorenko.votingsystem.model.Restaurant;
import ru.didorenko.votingsystem.repository.MenuItemRepository;
import java.time.LocalDate;
import java.util.List;

import static ru.didorenko.votingsystem.utill.MenuItemUtil.setWithoutRestaurant;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository repository;

    private final EntityManager entityManager;

    public List<MenuItem> findAllByDate(LocalDate date) {
        return repository.findAllByDateWithRestaurant(date);
    }

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
        menuItem.setRestaurant(entityManager.getReference(Restaurant.class, restaurantId));
        return repository.save(menuItem);
    }

    @CacheEvict(value = {"menuItems", "menuItemsByDate"}, allEntries = true)
    public void update(MenuItem menuItem, int restaurantId, int id) {
        MenuItem existingMenuItem = repository.getExistedByRestaurant(id, restaurantId);
        if (!existingMenuItem.getRestaurant().getId().equals(restaurantId)) {
            throw new IllegalArgumentException("Cannot change the restaurant of a MenuItem");
        }
        repository.save(setWithoutRestaurant(menuItem));
    }
}
