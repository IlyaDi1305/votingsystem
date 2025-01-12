package ru.didorenko.votingsystem.web.vote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import ru.didorenko.votingsystem.AbstractTest;
import ru.didorenko.votingsystem.repository.VoteRepository;
import ru.didorenko.votingsystem.utill.DateTimeUtil;
import ru.didorenko.votingsystem.web.user.UserTestData;
import java.time.*;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.didorenko.votingsystem.web.user.UserTestData.USER_ID;
import static ru.didorenko.votingsystem.web.vote.VoteTestData.VOTE_MATCHER;
import static ru.didorenko.votingsystem.web.vote.VoteTestData.VOTE_TO_1;

@SpringBootTest
@AutoConfigureMockMvc
class UserVoteControllerTest extends AbstractTest {

    @Autowired
    VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getAllByUserId() throws Exception {
        perform(get(UserVoteController.REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTE_TO_1));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    public void testCreateVote() throws Exception {
        try (MockedStatic<DateTimeUtil> dateUtilMockedStatic = mockStatic(DateTimeUtil.class)) {
            dateUtilMockedStatic.when(DateTimeUtil::getLocalDate).thenReturn(LocalDate.now()
                    .plusDays(1));
            dateUtilMockedStatic.when(DateTimeUtil::getCurrentTime).thenReturn(LocalTime.now());
            perform(post(UserVoteController.REST_URL)
                    .param("restaurantId", "1"))
                    .andExpect(status().isCreated());
        }
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    public void testUpdateVoteBeForDeadline() throws Exception {
        try (MockedStatic<DateTimeUtil> timeUtilMockedStatic = mockStatic(DateTimeUtil.class)) {
            timeUtilMockedStatic.when(DateTimeUtil::getCurrentTime).thenReturn(LocalTime.of(10, 59));
            perform(put(UserVoteController.REST_URL)
                    .param("restaurantId", "3")
                    .contentType(APPLICATION_JSON))
                    .andExpect(status().isNoContent());
            Assertions.assertEquals(3, voteRepository
                    .findByUserIdAndVoteDate(USER_ID, LocalDate.now()).getRestaurant().getId());
        }
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    public void testUpdateVoteAfterDeadline() throws Exception {
        try (MockedStatic<DateTimeUtil> timeUtilMockedStatic = mockStatic(DateTimeUtil.class)) {
            timeUtilMockedStatic.when(DateTimeUtil::getCurrentTime).thenReturn(LocalTime.of(11, 1));
            perform(put(UserVoteController.REST_URL)
                    .param("restaurantId", "3")
                    .contentType(APPLICATION_JSON))
                    .andExpect(status().isUnprocessableEntity());
            Assertions.assertEquals(1, voteRepository
                    .findByUserIdAndVoteDate(USER_ID, LocalDate.now()).getRestaurant().getId());
        }
    }
}