package ru.didorenko.votingsystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.didorenko.votingsystem.common.error.IllegalRequestDataException;
import ru.didorenko.votingsystem.common.error.NotFoundException;
import ru.didorenko.votingsystem.model.MenuItem;
import ru.didorenko.votingsystem.model.Restaurant;
import ru.didorenko.votingsystem.repository.MenuItemRepository;
import ru.didorenko.votingsystem.repository.RestaurantRepository;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository repository;

    private final RestaurantRepository restaurantRepository;

    public MenuItem getByIdMenuItems(int id) {
        return repository.getExistedById(id);
    }

    @Transactional
    @CacheEvict(value = "restaurantsWithMenuByDate", allEntries = true)
    public void delete(int id) {
        repository.deleteExisted(id);
    }

    @Transactional
    @CacheEvict(value = "restaurantsWithMenuByDate", allEntries = true)
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
    @CacheEvict(value = "restaurantsWithMenuByDate", allEntries = true)
    public void update(MenuItem menuItem, int restaurantId) {
        MenuItem existingMenuItem = repository.getExistedById(menuItem.getId());
        if (existingMenuItem.getRestaurant().getId() != restaurantId) {
            throw new IllegalRequestDataException("Changing the restaurant of a menu item is not allowed.");
        }
        Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
        Hibernate.initialize(restaurant);
        menuItem.setRestaurant(restaurant);
        repository.save(menuItem);
    }
}