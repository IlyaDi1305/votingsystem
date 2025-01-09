package ru.didorenko.votingsystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.didorenko.votingsystem.common.error.NotFoundException;
import ru.didorenko.votingsystem.model.MenuItem;
import ru.didorenko.votingsystem.model.Restaurant;
import ru.didorenko.votingsystem.repository.MenuItemRepository;
import ru.didorenko.votingsystem.repository.RestaurantRepository;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository repository;

    private final RestaurantRepository restaurantRepository;

    @Cacheable(value = "allMenuItemsByDate", key = "#date")
    public List<MenuItem> getAllByDate(LocalDate date) {
        return repository.getAllExistedByDate(date);
    }

    public MenuItem getByIdMenuItems(int id) {
        return repository.getExistedById(id);
    }

    @Cacheable(value = "menuItemsByDate", key = "#restaurantId + '_' + #date")
    public List<MenuItem> getAllByRestaurantIdAndMenuItemDate(int restaurantId, LocalDate date) {
        return repository.getExistedByRestaurantIdAndMenuItemDate(restaurantId, date);
    }

    @CacheEvict(value = "menuItemsByDate", allEntries = true)
    public void delete(int id) {
        repository.deleteExisted(id);
    }

    @Transactional
    @CacheEvict(value = {"menuItemsByDate", "allMenuItemsByDate"}, allEntries = true)
    public MenuItem create(MenuItem menuItem, int restaurantId) {
        try {
            Restaurant restaurantProxy = restaurantRepository.getReferenceById(restaurantId);
            Hibernate.initialize(restaurantProxy);
            menuItem.setRestaurant(restaurantProxy);
            return repository.save(menuItem);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("Restaurant with id " + restaurantId + " does not exist.");
        }
    }

    @Transactional
    @CacheEvict(value = {"menuItemsByDate", "allMenuItemsByDate"}, allEntries = true)
    public void update(MenuItem menuItem, int restaurantId) {
        try {
            Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
            Hibernate.initialize(restaurant);
            menuItem.setRestaurant(restaurant);
            repository.save(menuItem);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("Restaurant with id " + restaurantId + " does not exist.");
        }
    }
}
