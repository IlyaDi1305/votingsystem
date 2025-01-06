package ru.didorenko.votingsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.didorenko.votingsystem.common.error.NotFoundException;
import ru.didorenko.votingsystem.model.Vote;
import ru.didorenko.votingsystem.repository.RestaurantRepository;
import ru.didorenko.votingsystem.repository.UserRepository;
import ru.didorenko.votingsystem.repository.VoteRepository;
import ru.didorenko.votingsystem.to.VoteTo;
import ru.didorenko.votingsystem.utill.VoteUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.didorenko.votingsystem.utill.VoteUtil.createToList;
import static ru.didorenko.votingsystem.validation.VoteValidator.validateDeadline;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    private final RestaurantRepository restaurantRepository;

    private final UserRepository userRepository;

    @Cacheable(value = "votesByUser", key = "#userId")
    public List<VoteTo> getAllByUserId(int userId) {
        return createToList(voteRepository.getAllByUserId(userId));
    }

    @CacheEvict(value = "votesByUser", key = "#userId", allEntries = true)
    public Vote createVote(int restaurantId, int userId) {
        LocalDate today = LocalDate.now();
        LocalTime todayTime = LocalTime.now();
        Vote vote = voteRepository.findByUserIdAndVoteDate(userId, today);
        if (vote != null) {
            throw new IllegalStateException("Vote already exists for today. Use PUT to update.");
        }
        return voteRepository.save(new Vote(userRepository.getExisted(userId),
                restaurantRepository.getExisted(restaurantId), today, todayTime));
    }

    @CacheEvict(value = "votesByUser", key = "#userId", allEntries = true)
    public void updateVote(int restaurantId, int userId) {
        LocalDate today = LocalDate.now();
        LocalTime todayTime = LocalTime.now();
        validateDeadline(todayTime);
        Vote vote = voteRepository.findByUserIdAndVoteDate(userId, today);
        if (vote == null) {
            throw new NotFoundException("Vote not found");
        }
        voteRepository.save(VoteUtil.setVote(vote, restaurantRepository.getExisted(restaurantId), todayTime));
    }
}