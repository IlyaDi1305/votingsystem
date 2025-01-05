package ru.didorenko.votingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.didorenko.votingsystem.model.Restaurant;
import ru.didorenko.votingsystem.repository.RestaurantRepository;
import ru.didorenko.votingsystem.to.RestaurantTo;
import ru.didorenko.votingsystem.utill.RestaurantUtil;
import ru.didorenko.votingsystem.validation.UniqueRestaurantNameValidator;
import java.util.List;

import static ru.didorenko.votingsystem.utill.RestaurantUtil.createTo;
import static ru.didorenko.votingsystem.utill.RestaurantUtil.createToList;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository repository;
    @Autowired
    private UniqueRestaurantNameValidator nameValidator;

    @Cacheable(value = "restaurants", key = "#id")
    public RestaurantTo getExisted(int id) {
        return createTo(repository.getExisted(id));
    }

    public RestaurantTo getExistedByName(String name) {
        return createTo(repository.findByNameIgnoreCase(name));
    }

    @Cacheable(value = "restaurantsAll")
    public List<RestaurantTo> getAll() {
        return createToList(repository.findAll());
    }

    @CacheEvict(value = {"restaurants", "restaurantsByName", "restaurantsAll"}, allEntries = true)
    public RestaurantTo create(String name) {
        nameValidator.validate(name,null);
        return createTo(repository.save(RestaurantUtil.createNewWithName(name)));
    }

    @CacheEvict(value = {"restaurants", "restaurantsByName", "restaurantsAll"}, allEntries = true)
    public Restaurant update(int id, String newName) {
        nameValidator.validate(newName, id);
        Restaurant existingRestaurant = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant with id '" + id + "' not found"));
        return repository.save(RestaurantUtil.updateWithName(existingRestaurant, newName));
    }

    @CacheEvict(value = {"restaurants", "restaurantsByName", "restaurantsAll"}, allEntries = true)
    public void delete(int id) {
        repository.deleteExisted(id);
    }
}
