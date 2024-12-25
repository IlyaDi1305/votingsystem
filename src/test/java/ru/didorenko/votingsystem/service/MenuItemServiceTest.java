package ru.didorenko.votingsystem.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import ru.didorenko.votingsystem.model.MenuItem;
import ru.didorenko.votingsystem.model.Restaurant;
import ru.didorenko.votingsystem.repository.MenuItemRepository;
import java.time.LocalDate;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MenuItemServiceTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @InjectMocks
    private MenuItemService menuItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetExistedByRestaurant() {
        int id = 1;
        int restaurantId = 1;
        MenuItem menuItem = new MenuItem();
        menuItem.setId(id);
        menuItem.setRestaurant(new Restaurant(restaurantId, "rest"));

        when(menuItemRepository.getExistedByRestaurant(id, restaurantId)).thenReturn(menuItem);

        MenuItem result = menuItemService.getExistedByRestaurant(id, restaurantId);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(restaurantId, result.getRestaurant().getId());
        verify(menuItemRepository, times(1)).getExistedByRestaurant(id, restaurantId);
    }

    @Test
    void testFindAllByRestaurantIdAndDishDate() {
        int restaurantId = 1;
        LocalDate date = LocalDate.now();
        MenuItem menuItem = new MenuItem();
        menuItem.setDate(date);

        when(menuItemRepository.findAllByRestaurantIdAndDishDate(restaurantId, date))
                .thenReturn(List.of(menuItem));

        List<MenuItem> result = menuItemService.findAllByRestaurantIdAndDishDate(restaurantId, date);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(menuItemRepository, times(1)).findAllByRestaurantIdAndDishDate(restaurantId, date);
    }

    @Test
    void testDeleteExistedByRestaurant() {
        int id = 1;
        int restaurantId = 1;

        doNothing().when(menuItemRepository).deleteExistedByRestaurant(id, restaurantId);

        menuItemService.deleteExistedByRestaurant(id, restaurantId);

        verify(menuItemRepository, times(1)).deleteExistedByRestaurant(id, restaurantId);
    }

    @Test
    void testCreate() {
        int restaurantId = 1;
        MenuItem menuItem = new MenuItem();
        menuItem.setDate(LocalDate.now());
        Restaurant restaurant = new Restaurant(restaurantId, "rest");
        menuItem.setRestaurant(restaurant);

        when(entityManager.getReference(Restaurant.class, restaurantId)).thenReturn(restaurant);
        when(menuItemRepository.save(menuItem)).thenReturn(menuItem);

        MenuItem result = menuItemService.create(menuItem, restaurantId);

        assertNotNull(result);
        assertEquals(restaurantId, result.getRestaurant().getId());
        verify(menuItemRepository, times(1)).save(menuItem);
    }

    @Test
    void testUpdate() {
        int restaurantId = 1;
        int id = 1;
        MenuItem menuItem = new MenuItem();
        menuItem.setId(id);
        menuItem.setDate(LocalDate.now());
        Restaurant restaurant = new Restaurant(restaurantId, "rest");
        menuItem.setRestaurant(restaurant);

        when(entityManager.getReference(Restaurant.class, restaurantId)).thenReturn(restaurant);
        when(menuItemRepository.save(menuItem)).thenReturn(menuItem);

        MenuItem result = menuItemService.update(menuItem, restaurantId, id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(restaurantId, result.getRestaurant().getId());
        verify(menuItemRepository, times(1)).save(menuItem);
    }
}