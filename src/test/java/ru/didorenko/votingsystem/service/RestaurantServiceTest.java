package ru.didorenko.votingsystem.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.didorenko.votingsystem.AbstractTest;
import ru.didorenko.votingsystem.model.Restaurant;
import ru.didorenko.votingsystem.repository.RestaurantRepository;
import ru.didorenko.votingsystem.to.RestaurantTo;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantServiceTest extends AbstractTest {

    @Mock
    private RestaurantRepository repository;

    @InjectMocks
    private RestaurantService service;

    @Test
    void getExisted() {
        int id = 1;
        Restaurant restaurant = new Restaurant(id, "Test Restaurant");
        RestaurantTo expectedRestaurantTo = new RestaurantTo(id, "Test Restaurant");

        when(repository.getExisted(id)).thenReturn(restaurant);

        RestaurantTo result = service.getExisted(id);

        assertNotNull(result, "Result should not be null");
        assertEquals(expectedRestaurantTo.getId(), result.getId(), "Restaurant ID should match");
        assertEquals(expectedRestaurantTo.getName(), result.getName(), "Restaurant name should match");

        verify(repository).getExisted(id);
    }

    @Test
    void getAll() {
        List<Restaurant> restaurants = List.of(
                new Restaurant(1, "Restaurant 1"),
                new Restaurant(2, "Restaurant 2")
        );
        when(repository.findAll()).thenReturn(restaurants);

        List<RestaurantTo> result = service.getAll();

        assertNotNull(result, "Result should not be null");
        assertEquals(2, result.size(), "List size should be 2");
        verify(repository).findAll();
    }

    @Test
    void create() {
        String name = "New Restaurant";
        when(repository.getExistedByNameNot(name)).thenReturn(Optional.empty());
        Restaurant newRestaurant = new Restaurant(null, name);
        when(repository.save(any(Restaurant.class))).thenReturn(newRestaurant);

        Restaurant result = service.create(name);

        assertNotNull(result, "Result should not be null");
        assertEquals(name, result.getName(), "Restaurant name should match");

        verify(repository).getExistedByNameNot(name);
        verify(repository).save(any(Restaurant.class));
    }

    @Test
    void createThrowsExceptionIfNameExists() {
        String name = "Existing Restaurant";
        when(repository.getExistedByNameNot(name)).thenReturn(Optional.of(new Restaurant(1, name)));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.create(name));

        assertEquals("Restaurant with name 'Existing Restaurant' already exists", exception.getMessage());
        verify(repository).getExistedByNameNot(name);
        verify(repository, never()).save(any(Restaurant.class));
    }

    @Test
    void update() {
        int id = 1;
        String newName = "Updated Name";
        Restaurant existingRestaurant = new Restaurant(id, "Old Name");
        when(repository.findById(id)).thenReturn(Optional.of(existingRestaurant));
        when(repository.getExistedByNameNot(newName)).thenReturn(Optional.empty());
        when(repository.save(any(Restaurant.class))).thenReturn(new Restaurant(id, newName));

        Restaurant result = service.update(id, newName);

        assertNotNull(result, "Result should not be null");
        assertEquals(newName, result.getName(), "Updated name should match");

        verify(repository).findById(id);
        verify(repository).getExistedByNameNot(newName);
        verify(repository).save(any(Restaurant.class));
    }

    @Test
    void updateThrowsExceptionIfNotFound() {
        int id = 1;
        String newName = "Updated Name";
        when(repository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.update(id, newName));

        assertEquals("Restaurant with id '1' not found", exception.getMessage());
        verify(repository).findById(id);
        verify(repository, never()).save(any(Restaurant.class));
    }

    @Test
    void delete() {
        int id = 1;
        doNothing().when(repository).deleteExisted(id);

        service.delete(id);

        verify(repository).deleteExisted(id);
    }
}
