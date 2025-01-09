package ru.didorenko.votingsystem.web.vote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import ru.didorenko.votingsystem.AbstractTest;
import ru.didorenko.votingsystem.model.Vote;
import ru.didorenko.votingsystem.repository.VoteRepository;
import ru.didorenko.votingsystem.utill.TimeUtill;
import ru.didorenko.votingsystem.web.user.UserTestData;

import java.time.*;
import java.util.Base64;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VoteUserControllerTest extends AbstractTest {

    private String basicAuthHeader;

    @Autowired
    VoteRepository voteRepository;

    @BeforeEach
    public void setUp() {
        String username = "user@yandex.ru";
        String password = "password";
        String auth = username + ":" + password;
        basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }

    @Test
    void getAllByUserId() {
    }

    @Test
    public void testCreateVote() throws Exception {
        perform(post(VoteUserController.REST_URL)
                        .param("restaurantId", "1")
                        .header("Authorization", basicAuthHeader)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void testUpdateVoteBeForDeadline() throws Exception {
        try (MockedStatic<TimeUtill> timeUtilMockedStatic = mockStatic(TimeUtill.class)) {
            timeUtilMockedStatic.when(TimeUtill::getCurrentTime).thenReturn(LocalTime.of(10, 59));
            perform(put(VoteUserController.REST_URL)
                            .param("restaurantId", "3")
                            .header("Authorization", basicAuthHeader)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
            Vote updatedVote = voteRepository.findByUserIdAndVoteDate(UserTestData.USER_ID, LocalDate.now());
            Assertions.assertEquals(3, updatedVote.getRestaurant().getId());
        }
    }

    @Test
    public void testUpdateVoteAfterDeadline() throws Exception {
        try (MockedStatic<TimeUtill> timeUtilMockedStatic = mockStatic(TimeUtill.class)) {
            timeUtilMockedStatic.when(TimeUtill::getCurrentTime).thenReturn(LocalTime.of(11, 1));
            perform(put(VoteUserController.REST_URL)
                    .param("restaurantId", "3")
                    .header("Authorization", basicAuthHeader)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnprocessableEntity());
            Vote updatedVote = voteRepository.findByUserIdAndVoteDate(UserTestData.USER_ID, LocalDate.now());
            Assertions.assertNotEquals(3, updatedVote.getRestaurant().getId());
        }
    }
}