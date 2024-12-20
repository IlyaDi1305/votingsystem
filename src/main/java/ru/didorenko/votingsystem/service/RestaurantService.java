package ru.didorenko.votingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.didorenko.votingsystem.model.Restaurant;
import ru.didorenko.votingsystem.repository.RestaurantRepository;
import ru.didorenko.votingsystem.utill.RestaurantUtill;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository repository;

    @Cacheable(value = "restaurants", key = "#id")
    public Restaurant getExisted(int id) {
        return repository.getExisted(id);
    }

    @Cacheable(value = "restaurantsAll")
    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    @Cacheable(value = "restaurantsByName", key = "#name")
    public Restaurant getExistedByName(String name) {
        return repository.getExistedByName(name);
    }

    @CacheEvict(value = "restaurantsAll", allEntries = true)
    public Restaurant create(String name) {
        if (repository.getExistedByNameNot(name).isPresent()) {
            throw new IllegalArgumentException("Restaurant with name '" + name + "' already exists");
        }
        return repository.save(RestaurantUtill.createNewWithName(name));
    }

    @CacheEvict(value = {"restaurants", "restaurantsByName"}, key = "#id")
    public Restaurant update(int id, String newName) {
        Optional<Restaurant> existingRestaurant = repository.findById(id);
        if (existingRestaurant.isEmpty()) {
            throw new IllegalArgumentException("Restaurant with id '" + id + "' not found");
        }
        if (repository.getExistedByNameNot(newName).isPresent() &&
                !existingRestaurant.get().getName().equalsIgnoreCase(newName)) {
            throw new IllegalArgumentException("Another restaurant with name '" + newName + "' already exists");
        }
        return repository.save(RestaurantUtill.updateWithName(existingRestaurant.get(), newName));
    }

    @CacheEvict(value = "restaurants", key = "#id")
    public void delete(int id) {
        repository.deleteExisted(id);
    }
}
