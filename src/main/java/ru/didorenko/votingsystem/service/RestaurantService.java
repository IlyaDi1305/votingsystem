package ru.didorenko.votingsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.didorenko.votingsystem.model.Restaurant;
import ru.didorenko.votingsystem.repository.RestaurantRepository;
import ru.didorenko.votingsystem.to.RestaurantTo;
import ru.didorenko.votingsystem.utill.RestaurantUtil;
import ru.didorenko.votingsystem.validation.UniqueRestaurantNameValidator;
import java.time.LocalDate;
import java.util.List;

import static ru.didorenko.votingsystem.utill.RestaurantUtil.createTo;
import static ru.didorenko.votingsystem.utill.RestaurantUtil.createToList;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;

    private final UniqueRestaurantNameValidator nameValidator;

    @Cacheable(value = "restaurantsWithMenuByDate", key = "#menuItemDate")
    public List<Restaurant> getAllWithMenuItemByDate(LocalDate menuItemDate) {
        return repository.getAllWithMenuItemByDate(menuItemDate);
    }

    public List<Restaurant> getAllWithMenuItem(){
        return repository.getAllWithMenuItem();
    }

    public RestaurantTo getExistedById(int id) {
        return createTo(repository.getExisted(id));
    }

    public RestaurantTo getByNameWithoutMenuItems(String name) {
        return createTo(repository.getExistedByName(name));
    }

    public List<RestaurantTo> getAllWithoutMenuItems() {
        return createToList(repository.findAll());
    }

    @Transactional
    @CacheEvict(value = "restaurantsWithMenuByDate", allEntries = true)
    public RestaurantTo create(String name) {
        nameValidator.nameValidator(name);
        return createTo(repository.save(RestaurantUtil.createNewWithName(name)));
    }

    @Transactional
    @CacheEvict(value = "restaurantsWithMenuByDate", allEntries = true)
    public Restaurant update(int id, String newName) {
        nameValidator.nameValidator(newName, id);
        Restaurant existingRestaurant = repository.getExisted(id);
        return repository.save(RestaurantUtil.updateWithName(existingRestaurant, newName));
    }

    @Transactional
    @CacheEvict(value = "restaurantsWithMenuByDate", allEntries = true)
    public void delete(int id) {
        repository.deleteExisted(id);
    }
}