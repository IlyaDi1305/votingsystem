package ru.didorenko.votingsystem.web.restaurant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.didorenko.votingsystem.AbstractTest;
import ru.didorenko.votingsystem.model.Restaurant;
import ru.didorenko.votingsystem.repository.RestaurantRepository;
import ru.didorenko.votingsystem.web.user.UserTestData;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.didorenko.votingsystem.web.restaurant.RestaurantTestData.*;
import static ru.didorenko.votingsystem.web.user.UserTestData.ADMIN_MAIL;

@SpringBootTest
@AutoConfigureMockMvc
class AdminRestaurantControllerTest extends AbstractTest {

    private static final String REST_URL = AdminRestaurantController.REST_URL;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getAllWithMenuByDate() throws Exception {
        perform(get(REST_URL + "/menuItems/by-date")
                .param("menuItemDate", TEST_DATE.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_MATCHER.contentJson(RESTAURANTS));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllWithMenu() throws Exception {
        perform(get(REST_URL + "/menuItems"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_MATCHER.contentJson(RESTAURANTS_PLUS_DAY));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        String newRestaurantJson = """
                {
                    "name": "New Restaurant"
                }
                """;
        perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newRestaurantJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.containsString(REST_URL)))
                .andExpect(jsonPath("$.name").value("New Restaurant"));
        Restaurant createdMenuItem = restaurantRepository.findAll().stream()
                .filter(restaurant -> "New Restaurant".equals(restaurant.getName()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("No new restaurant found"));
        Assertions.assertEquals("New Restaurant", createdMenuItem.getName());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        String updatedRestaurantJson = """
                {
                    "name": "Updated Restaurant"
                }
                """;
        perform(put(REST_URL + "/{restaurantId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedRestaurantJson))
                .andExpect(status().isNoContent());
        Assertions.assertEquals("Updated Restaurant", restaurantRepository.findById(1).orElseThrow().getName());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/{id}", 1))
                .andExpect(status().isNoContent());
        Assertions.assertFalse(restaurantRepository.existsById(1), "The restaurant should be removed");
    }
}