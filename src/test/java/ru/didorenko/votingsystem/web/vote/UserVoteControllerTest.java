package ru.didorenko.votingsystem.web.vote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import ru.didorenko.votingsystem.AbstractTest;
import ru.didorenko.votingsystem.model.Vote;
import ru.didorenko.votingsystem.repository.VoteRepository;
import ru.didorenko.votingsystem.utill.DateTimeUtil;
import ru.didorenko.votingsystem.web.user.UserTestData;

import java.time.*;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.didorenko.votingsystem.web.vote.VoteTestData.VOTE_MATCHER;

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
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(List.of(VoteTestData.voteTo1)));
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
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
            Vote updatedVote = voteRepository.findByUserIdAndVoteDate(UserTestData.USER_ID, LocalDate.now());
            Assertions.assertEquals(3, updatedVote.getRestaurant().getId());
        }
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    public void testUpdateVoteAfterDeadline() throws Exception {
        try (MockedStatic<DateTimeUtil> timeUtilMockedStatic = mockStatic(DateTimeUtil.class)) {
            timeUtilMockedStatic.when(DateTimeUtil::getCurrentTime).thenReturn(LocalTime.of(11, 1));
            perform(put(UserVoteController.REST_URL)
                    .param("restaurantId", "3")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnprocessableEntity());
            Vote updatedVote = voteRepository.findByUserIdAndVoteDate(UserTestData.USER_ID, LocalDate.now());
            Assertions.assertEquals(1, updatedVote.getRestaurant().getId());
        }
    }
}