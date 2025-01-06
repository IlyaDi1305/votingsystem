package ru.didorenko.votingsystem.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.didorenko.votingsystem.model.Restaurant;
import ru.didorenko.votingsystem.repository.RestaurantRepository;

@Component
@RequiredArgsConstructor
public class UniqueRestaurantNameValidator {

    public static final String EXCEPTION_DUPLICATE_NAME = "Restaurant with this name already exists";

    private final RestaurantRepository repository;

    public void validate(String name, Integer id) {
        Restaurant existingRestaurant = repository.findByNameIgnoreCase(name);
        if (existingRestaurant != null && (id == null || !existingRestaurant.getId().equals(id))) {
            throw new IllegalArgumentException(EXCEPTION_DUPLICATE_NAME);
        }
    }
}