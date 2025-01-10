package ru.didorenko.votingsystem.web.menuitem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import ru.didorenko.votingsystem.AbstractTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.didorenko.votingsystem.web.user.UserTestData.USER_MAIL;

@SpringBootTest
@AutoConfigureMockMvc
public class UserMenuItemControllerTest extends AbstractTest {

    private static final String REST_URL = UserMenuItemController.REST_URL;

    @Test
    @WithUserDetails(value = USER_MAIL)
    public void testGetMenuItemById() throws Exception {
        perform(get(REST_URL + "/menuItem/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    public void testGetAllMenuItemsToday() throws Exception {
        perform(get(REST_URL + "/menuItem/allToday")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    public void testGetAllMenuItemsByRestaurantIdToday() throws Exception {
        perform(get(REST_URL + "/{restaurantId}/menuItem/today", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }
}