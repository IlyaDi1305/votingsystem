package ru.didorenko.votingsystem.web.restaurant;

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
public class UserRestaurantControllerTest extends AbstractTest {

    private static final String REST_URL = UserRestaurantController.REST_URL;

    @Test
    @WithUserDetails(value = USER_MAIL)
    public void testGetRestaurantById() throws Exception {
        perform(get(REST_URL + "/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    public void testGetAllRestaurants() throws Exception {
        perform(get(REST_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    public void testGetRestaurantByName() throws Exception {
        perform(get(REST_URL + "/by-name")
                .param("name", "Pizzeria Bacco")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Pizzeria Bacco"));
    }
}