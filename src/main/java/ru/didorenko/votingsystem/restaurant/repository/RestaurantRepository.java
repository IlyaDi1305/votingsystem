package ru.didorenko.votingsystem.restaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.didorenko.votingsystem.common.BaseRepository;
import ru.didorenko.votingsystem.common.error.NotFoundException;
import ru.didorenko.votingsystem.restaurant.model.Restaurant;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query("SELECT r FROM Restaurant r WHERE LOWER(r.name) = LOWER(:name)")
    Optional<Restaurant> findByNameIgnoreCase(String name);

    default Restaurant getExistedByName(String name) {
        return findByNameIgnoreCase(name).orElseThrow(() -> new NotFoundException("Restaurant with name=" + name + " not found"));
    }
}
