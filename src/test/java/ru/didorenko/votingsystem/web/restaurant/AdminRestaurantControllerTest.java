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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.didorenko.votingsystem.web.user.UserTestData.ADMIN_MAIL;

@SpringBootTest
@AutoConfigureMockMvc
class AdminRestaurantControllerTest extends AbstractTest {

    private static final String REST_URL = AdminRestaurantController.REST_URL;

    @Autowired
    RestaurantRepository restaurantRepository;

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
        List<Restaurant> restaurants = restaurantRepository.findAll();
        Restaurant createdMenuItem = restaurants.stream()
                .filter(restaurant -> "New Restaurant".equals(restaurant.getName()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Новый ресторан не найден"));
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
        Restaurant restaurant = restaurantRepository.findById(1).orElseThrow();
        Assertions.assertEquals("Updated Restaurant", restaurant.getName());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/{id}", 1))
                .andExpect(status().isNoContent());
        Assertions.assertFalse(restaurantRepository.existsById(1),"Ресторан должен быть удалено");
    }
}