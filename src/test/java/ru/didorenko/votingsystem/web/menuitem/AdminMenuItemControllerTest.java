package ru.didorenko.votingsystem.web.menuitem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import ru.didorenko.votingsystem.AbstractTest;
import ru.didorenko.votingsystem.model.MenuItem;
import ru.didorenko.votingsystem.repository.MenuItemRepository;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.didorenko.votingsystem.web.user.UserTestData.ADMIN_MAIL;

@SpringBootTest
@AutoConfigureMockMvc
class AdminMenuItemControllerTest extends AbstractTest {

    @Autowired
    MenuItemRepository menuItemRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void testCreateMenuItem() throws Exception {
        String newMenuItemJson = """
                {
                    "name": "New Dish",
                    "description": "New Dish description",
                    "price": 150,
                    "menuItemDate": "%s"
                }
                """.formatted(LocalDate.now());

        perform(post(AdminMenuItemController.REST_URL + "/{restaurantId}/menuItems", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newMenuItemJson))
                .andExpect(status().isCreated());
        List<MenuItem> menuItems = menuItemRepository.getAllExistedByDate(LocalDate.now());
        MenuItem createdMenuItem = menuItems.stream()
                .filter(menuItem -> "New Dish".equals(menuItem.getName()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Новое блюдо не найдено"));
        Assertions.assertEquals(150, createdMenuItem.getPrice());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void testUpdateMenuItem() throws Exception {
        String updatedMenuItemJson = """
                {
                    "name": "Updated Dish",
                    "description": "Updated Dish description",
                    "price": 200,
                    "menuItemDate": "%s"
                }
                """.formatted(LocalDate.now());

        perform(put(AdminMenuItemController.REST_URL + "/{restaurantId}/menuItems/{id}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedMenuItemJson))
                .andExpect(status().isNoContent());

        MenuItem updatedMenuItem = menuItemRepository.findById(1).orElseThrow();
        Assertions.assertEquals("Updated Dish", updatedMenuItem.getName());
        Assertions.assertEquals(200, updatedMenuItem.getPrice());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void testDeleteMenuItem() throws Exception {
        perform(delete(AdminMenuItemController.REST_URL + "/menuItems/{id}", 1))
                .andExpect(status().isNoContent());
        Assertions.assertFalse(menuItemRepository.existsById(1), "Блюдо должно быть удалено");
    }
}