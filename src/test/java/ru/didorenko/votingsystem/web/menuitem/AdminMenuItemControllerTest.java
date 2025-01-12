package ru.didorenko.votingsystem.web.menuitem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import ru.didorenko.votingsystem.AbstractTest;
import ru.didorenko.votingsystem.model.MenuItem;
import ru.didorenko.votingsystem.repository.MenuItemRepository;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.didorenko.votingsystem.web.user.UserTestData.ADMIN_MAIL;

class AdminMenuItemControllerTest extends AbstractTest {

    public static final String REST_URL = AdminMenuItemController.REST_URL;

    @Autowired
    MenuItemRepository menuItemRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void getById() throws Exception {
        perform(get(REST_URL + "/menuItems/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

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
        MenuItem createdMenuItem = menuItemRepository.getAllExistedByDate(LocalDate.now()).stream()
                .filter(menuItem -> "New Dish".equals(menuItem.getName()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("No new dish found"));
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
        Assertions.assertFalse(menuItemRepository.existsById(1), "The dish must be removed");
    }
}