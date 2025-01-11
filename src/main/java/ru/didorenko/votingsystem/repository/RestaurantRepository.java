package ru.didorenko.votingsystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.didorenko.votingsystem.common.BaseRepository;
import ru.didorenko.votingsystem.common.error.NotFoundException;
import ru.didorenko.votingsystem.model.Restaurant;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT r FROM Restaurant r WHERE LOWER(r.name) = LOWER(:name)")
    Restaurant findByNameIgnoreCase(String name);

    default Restaurant getExistedByName(String name) {
        Restaurant restaurant = findByNameIgnoreCase(name);
        if (restaurant == null) {
            throw new NotFoundException("Restaurant not found with name=" + name);
        }
        return restaurant;
    }

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menuItems")
    List<Restaurant> findAllWithMenuItem();

    default List<Restaurant> getAllWithMenuItem() {
        List<Restaurant> restaurants = findAllWithMenuItem();
        if (restaurants.isEmpty()) {
            throw new NotFoundException("No restaurants found with menu items.");
        }
        return restaurants;
    }

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menuItems m WHERE m.menuItemDate = :menuItemDate")
    List<Restaurant> findAllWithMenuItemByDate(LocalDate menuItemDate);

    default List<Restaurant> getAllWithMenuItemByDate(LocalDate menuItemDate) {
        List<Restaurant> restaurants = findAllWithMenuItemByDate(menuItemDate);
        if (restaurants.isEmpty()) {
            throw new NotFoundException("No restaurants found with menu items for date: " + menuItemDate);
        }
        return restaurants;
    }
}