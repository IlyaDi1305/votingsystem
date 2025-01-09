package ru.didorenko.votingsystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.didorenko.votingsystem.common.BaseRepository;
import ru.didorenko.votingsystem.common.error.NotFoundException;
import ru.didorenko.votingsystem.model.Restaurant;

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
}
