package ru.didorenko.votingsystem.restaurant.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.didorenko.votingsystem.common.BaseRepository;
import ru.didorenko.votingsystem.common.error.NotFoundException;
import ru.didorenko.votingsystem.restaurant.model.MenuItem;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends BaseRepository<MenuItem> {

    @Query("SELECT mi FROM MenuItem mi WHERE mi.id = :id AND mi.restaurant.id = :restaurantId")
    Optional<MenuItem> findByIdAndRestaurantId(@Param("id") int id, @Param("restaurantId") int restaurantId);

    default MenuItem getExistedByRestaurant(int id, int restaurantId) {
        return findByIdAndRestaurantId(id, restaurantId)
                .orElseThrow(() -> new NotFoundException("Menu item not found for restaurant with id=" + restaurantId));
    }

    @Modifying
    @Transactional
    @Query("DELETE FROM MenuItem mi WHERE mi.id = :id AND mi.restaurant.id = :restaurantId")
    int deleteByIdAndRestaurantId(@Param("id") int id, @Param("restaurantId") int restaurantId);

    default void deleteExistedByRestaurant(int id, int restaurantId) {
        int rowsAffected = deleteByIdAndRestaurantId(id, restaurantId);
        if (rowsAffected == 0) {
            throw new NotFoundException("Menu item not found or does not belong to restaurant with id=" + restaurantId);
        }
    }
}