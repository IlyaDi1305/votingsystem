package ru.didorenko.votingsystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import ru.didorenko.votingsystem.model.Restaurant;
import ru.didorenko.votingsystem.model.User;
import ru.didorenko.votingsystem.model.Vote;
import ru.didorenko.votingsystem.repository.RestaurantRepository;
import ru.didorenko.votingsystem.repository.UserRepository;
import ru.didorenko.votingsystem.repository.VoteRepository;
import ru.didorenko.votingsystem.to.VoteTo;
import ru.didorenko.votingsystem.web.user.UserTestData;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static ru.didorenko.votingsystem.web.user.UserTestData.USER_ID;

class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private VoteService voteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllByUserId() {
        Vote vote = new Vote();
        vote.setId(1);
        vote.setUser(UserTestData.user);
        vote.setRestaurant(new Restaurant(1, "Restaurant"));
        vote.setDate(LocalDate.now());

        when(voteRepository.getAllByUserId(USER_ID)).thenReturn(List.of(vote));

        List<VoteTo> votes = voteService.getAllByUserId(USER_ID);

        assertNotNull(votes);
        assertEquals(1, votes.size());
        assertEquals(vote.getId(), votes.get(0).getId());
    }

    @Test
    void createOrUpdateVote_create() {
        int restaurantId = 1;
        Restaurant restaurant = new Restaurant(restaurantId, "Restaurant");

        when(restaurantRepository.getExisted(restaurantId)).thenReturn(restaurant);
        when(userRepository.getExisted(USER_ID)).thenReturn(UserTestData.user);
        when(voteRepository.findByUserIdAndDate(USER_ID, LocalDate.now())).thenReturn(Optional.empty());
        when(voteRepository.save(any(Vote.class))).thenReturn(new Vote(UserTestData.user, restaurant));

        Vote vote = voteService.createOrUpdateVote(restaurantId, USER_ID);

        assertNotNull(vote);
        assertEquals(restaurantId, vote.getRestaurant().getId());
        assertEquals(USER_ID, vote.getUser().getId());
    }

    @Test
    void createOrUpdateVote_update() {
        int restaurantId = 1;
        Restaurant restaurant = new Restaurant(restaurantId, "Restaurant");
        Vote existingVote = new Vote(UserTestData.user, restaurant);
        existingVote.setDate(LocalDate.now());

        when(voteRepository.findByUserIdAndDate(UserTestData.USER_ID, LocalDate.now())).thenReturn(Optional.of(existingVote));
        when(restaurantRepository.getExisted(restaurantId)).thenReturn(restaurant);
        when(voteRepository.updateAndSave(existingVote, restaurant)).thenReturn(existingVote);

        Vote vote = voteService.createOrUpdateVote(restaurantId, USER_ID);

        assertNotNull(vote);
        assertEquals(restaurantId, vote.getRestaurant().getId());
        verify(voteRepository, times(1)).updateAndSave(existingVote, restaurant);
    }

    @Test
    void getVoteCountForRestaurantToday() {
        int restaurantId = 1;
        int voteCount = 5;

        when(voteRepository.countVotesForRestaurantToday(restaurantId, LocalDate.now())).thenReturn(voteCount);
        int count = voteService.getVoteCountForRestaurantToday(restaurantId);
        assertEquals(voteCount, count);
    }

}
