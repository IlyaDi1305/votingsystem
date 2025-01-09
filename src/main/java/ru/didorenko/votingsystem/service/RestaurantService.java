package ru.didorenko.votingsystem.service;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;

    private final UniqueRestaurantNameValidator nameValidator;

    public RestaurantTo getExisted(int id) {
        return createTo(repository.getExisted(id));
    }

    public RestaurantTo getByName(String name) {
        return createTo(repository.getExistedByName(name));
    }

    @Cacheable(value = "restaurantsAll")
    public List<RestaurantTo> getAll() {
        return createToList(repository.findAll());
    }

    @CacheEvict(value = "restaurantsAll", allEntries = true)
    public RestaurantTo create(String name) {
        nameValidator.validate(name);
        return createTo(repository.save(RestaurantUtil.createNewWithName(name)));
    }

    @CacheEvict(value = "restaurantsAll", allEntries = true)
    public Restaurant update(int id, String newName) {
        nameValidator.validate(newName, id);
        Restaurant existingRestaurant = repository.getExisted(id);
        return repository.save(RestaurantUtil.updateWithName(existingRestaurant, newName));
    }

    @CacheEvict(value = "restaurantsAll", allEntries = true)
    public void delete(int id) {
        repository.deleteExisted(id);
    }
}
