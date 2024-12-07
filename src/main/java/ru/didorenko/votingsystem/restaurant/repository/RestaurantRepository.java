package ru.didorenko.votingsystem.restaurant.repository;

import org.springframework.stereotype.Repository;
import ru.didorenko.votingsystem.common.BaseRepository;
import ru.didorenko.votingsystem.restaurant.model.Restaurant;

@Repository
public interface RestaurantRepository extends BaseRepository<Restaurant> {
}
