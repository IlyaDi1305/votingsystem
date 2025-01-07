package ru.didorenko.votingsystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.didorenko.votingsystem.common.error.DuplicateVoteException;
import ru.didorenko.votingsystem.common.error.NotFoundException;
import ru.didorenko.votingsystem.model.Restaurant;
import ru.didorenko.votingsystem.model.User;
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

    @Transactional
    @CacheEvict(value = "votesByUser", key = "#userId", allEntries = true)
    public Vote createVote(int restaurantId, int userId) {
        LocalDate today = LocalDate.now();
        LocalTime todayTime = LocalTime.now();
        if (voteRepository.findByUserIdAndVoteDate(userId, today) != null) {
            throw new DuplicateVoteException("Vote already exists for today. Use PUT to update.");
        }

        Restaurant restaurantProxy;
        try {
            restaurantProxy = restaurantRepository.getReferenceById(restaurantId);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("Restaurant with id " + restaurantId + " does not exist.");
        }

        User userProxy;
        try {
            userProxy = userRepository.getReferenceById(userId);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("User with id " + userId + " does not exist.");
        }
        return voteRepository.save(new Vote(userProxy, restaurantProxy, today, todayTime));
    }

    @Transactional
    @CacheEvict(value = "votesByUser", key = "#userId", allEntries = true)
    public void updateVote(int restaurantId, int userId) {
        LocalDate today = LocalDate.now();
        LocalTime todayTime = LocalTime.now();
        validateDeadline(todayTime);
        Vote vote = voteRepository.findByUserIdAndVoteDate(userId, today);
        if (vote == null) {
            throw new NotFoundException("Vote not found");
        }
        try {
            Restaurant restaurantProxy = restaurantRepository.getReferenceById(restaurantId);
            voteRepository.save(VoteUtil.setVote(vote, restaurantProxy, todayTime));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("Restaurant with id " + restaurantId + " does not exist");
        }
    }
}