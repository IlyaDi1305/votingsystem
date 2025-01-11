package ru.didorenko.votingsystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.didorenko.votingsystem.common.BaseRepository;
import ru.didorenko.votingsystem.common.error.NotFoundException;
import ru.didorenko.votingsystem.model.MenuItem;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MenuItemRepository extends BaseRepository<MenuItem> {

    @Query("SELECT mi FROM MenuItem mi JOIN FETCH mi.restaurant WHERE mi.id = :id")
    MenuItem findByIdWithRestaurant(int id);

    default MenuItem getExistedById(int id) {
        MenuItem menuItem = findByIdWithRestaurant(id);
        if (menuItem == null) {
            throw new NotFoundException("Menu item not found with id=" + id);
        }
        return menuItem;
    }

    @Query("SELECT m FROM MenuItem m JOIN FETCH m.restaurant r WHERE m.menuItemDate = :menuItemDate ORDER BY r.id")
    List<MenuItem> findAllByDateWithRestaurant(LocalDate menuItemDate);

    default List<MenuItem> getAllExistedByDate(LocalDate menuItemDate) {
        List<MenuItem> menuItems = findAllByDateWithRestaurant(menuItemDate);
        if (menuItems.isEmpty()) {
            throw new NotFoundException("No menu items found for date=" + menuItemDate);
        }
        return menuItems;
    }
}