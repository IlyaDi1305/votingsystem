package ru.didorenko.votingsystem.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.didorenko.votingsystem.common.error.NotFoundException;
import ru.didorenko.votingsystem.model.Vote;
import ru.didorenko.votingsystem.repository.RestaurantRepository;
import ru.didorenko.votingsystem.repository.UserRepository;
import ru.didorenko.votingsystem.repository.VoteRepository;
import ru.didorenko.votingsystem.to.VoteTo;
import java.time.LocalDate;
import java.util.List;

import static ru.didorenko.votingsystem.utill.VoteUtil.createToList;
import static ru.didorenko.votingsystem.validation.VoteValidator.validateDeadline;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Cacheable(value = "votesByUser", key = "#userId")
    public List<VoteTo> getAllByUserId(int userId) {
        return createToList(voteRepository.getAllByUserId(userId));
    }

    @CacheEvict(value = "votesByUser", key = "#userId", allEntries = true)
    public Vote createVote(int restaurantId, int userId) {
        LocalDate today = LocalDate.now();
        validateDeadline();
        if (voteRepository.findByUserIdAndVoteDate(userId, today).isPresent()) {
            throw new IllegalStateException("Vote already exists for today. Use PUT to update.");
        }
        return voteRepository.save(new Vote(userRepository.getExisted(userId), restaurantRepository.getExisted(restaurantId)));
    }

    @CacheEvict(value = "votesByUser", key = "#userId", allEntries = true)
    public void updateVote(int restaurantId, int userId) {
        LocalDate today = LocalDate.now();
        validateDeadline();
        Vote existingVote = voteRepository.findByUserIdAndVoteDate(userId, today).orElseThrow(() -> new NotFoundException("Vote not found"));
        voteRepository.update(existingVote, restaurantRepository.getExisted(restaurantId));
    }
}