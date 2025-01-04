package ru.didorenko.votingsystem.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ru.didorenko.votingsystem.AbstractTest;
import ru.didorenko.votingsystem.model.MenuItem;
import ru.didorenko.votingsystem.model.Restaurant;
import ru.didorenko.votingsystem.repository.MenuItemRepository;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MenuItemServiceTest extends AbstractTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private MenuItemService menuItemService;

    @Test
    void testGetExistedByRestaurant() {
        int id = 1;
        int restaurantId = 1;
        MenuItem menuItem = new MenuItem();
        menuItem.setId(id);
        menuItem.setRestaurant(new Restaurant(restaurantId, "Test Restaurant"));

        when(menuItemRepository.getExistedByRestaurant(id, restaurantId)).thenReturn(menuItem);

        MenuItem result = menuItemService.getExistedByRestaurant(id, restaurantId);

        assertNotNull(result, "MenuItem should not be null");
        assertEquals(id, result.getId(), "MenuItem ID should match");
        assertEquals(restaurantId, result.getRestaurant().getId(), "Restaurant ID should match");
        verify(menuItemRepository).getExistedByRestaurant(id, restaurantId);
    }

    @Test
    void testFindAllByRestaurantIdAndDishDate() {
        int restaurantId = 1;
        LocalDate date = LocalDate.now();
        MenuItem menuItem = new MenuItem();
        menuItem.setMenuItemDate(date);

        when(menuItemRepository.findAllByRestaurantIdAndDishDate(restaurantId, date))
                .thenReturn(List.of(menuItem));

        List<MenuItem> result = menuItemService.findAllByRestaurantIdAndDishDate(restaurantId, date);

        assertNotNull(result, "Result list should not be null");
        assertFalse(result.isEmpty(), "Result list should not be empty");
        verify(menuItemRepository).findAllByRestaurantIdAndDishDate(restaurantId, date);
    }

    @Test
    void testDeleteExistedByRestaurant() {
        int id = 1;
        int restaurantId = 1;

        doNothing().when(menuItemRepository).deleteExistedByRestaurant(id, restaurantId);

        menuItemService.deleteExistedByRestaurant(id, restaurantId);

        verify(menuItemRepository).deleteExistedByRestaurant(id, restaurantId);
    }

    @Test
    void testCreate() {
        int restaurantId = 1;
        MenuItem menuItem = new MenuItem();
        menuItem.setMenuItemDate(LocalDate.now());
        Restaurant restaurant = new Restaurant(restaurantId, "Test Restaurant");
        menuItem.setRestaurant(restaurant);

        when(entityManager.getReference(Restaurant.class, restaurantId)).thenReturn(restaurant);
        when(menuItemRepository.save(menuItem)).thenReturn(menuItem);

        MenuItem result = menuItemService.create(menuItem, restaurantId);

        assertNotNull(result, "Created MenuItem should not be null");
        assertEquals(restaurantId, result.getRestaurant().getId(), "Restaurant ID should match");
        verify(menuItemRepository).save(menuItem);
    }

    @Test
    void testUpdate() {
        int restaurantId = 1;
        int id = 1;
        MenuItem menuItem = new MenuItem();
        menuItem.setId(id);
        menuItem.setMenuItemDate(LocalDate.now());
        Restaurant restaurant = new Restaurant(restaurantId, "Test Restaurant");
        menuItem.setRestaurant(restaurant);

        when(entityManager.getReference(Restaurant.class, restaurantId)).thenReturn(restaurant);
        when(menuItemRepository.save(menuItem)).thenReturn(menuItem);

        MenuItem result = menuItemService.update(menuItem, restaurantId, id);

        assertNotNull(result, "Updated MenuItem should not be null");
        assertEquals(id, result.getId(), "MenuItem ID should match");
        assertEquals(restaurantId, result.getRestaurant().getId(), "Restaurant ID should match");
        verify(menuItemRepository).save(menuItem);
    }
}