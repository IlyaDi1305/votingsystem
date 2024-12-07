package ru.didorenko.votingsystem.restaurant.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.didorenko.votingsystem.restaurant.model.Restaurant;
import ru.didorenko.votingsystem.restaurant.repository.RestaurantRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RestaurantService {

    RestaurantRepository restaurantRepository;

    public Restaurant getById(Integer id) {
        log.info("IN RestaurantService get {}", id);
        return restaurantRepository.getExisted(id);
    }

    public void save(Restaurant restaurant) {
        log.info("IN RestaurantService save {}", restaurant);
        restaurantRepository.save(restaurant);
    }

    public void delete(Integer id) {
        log.info("IN RestaurantService delete {}", id);
        restaurantRepository.deleteExisted(id);
    }

    public List<Restaurant> getAll() {
        log.info("IN RestaurantService getAll");
        return restaurantRepository.findAll();
    }
}
