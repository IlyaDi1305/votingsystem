package ru.didorenko.votingsystem.vote.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.didorenko.votingsystem.restaurant.model.Menu;
import ru.didorenko.votingsystem.restaurant.repository.RestaurantRepository;
import ru.didorenko.votingsystem.user.repository.UserRepository;
import ru.didorenko.votingsystem.vote.model.Vote;
import ru.didorenko.votingsystem.vote.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class VotingService {
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Vote> getAll() {
        log.info("IN MenuService getAll");
        return voteRepository.findAll();
    }

    public Vote castVote(Integer userId, Integer restaurantId) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        Optional<Vote> existingVote = voteRepository.findByUserIdAndDate(userId, today);
        if (existingVote.isPresent() && now.isAfter(LocalTime.of(11, 0))) {
            throw new IllegalStateException("Голос нельзя изменить после 11:00.");
        }

        Vote vote = existingVote.orElse(new Vote());
        vote.setDate(today);
        vote.setVoteTime(now);
        vote.setUser(userRepository.findById(userId).orElseThrow());
        vote.setRestaurant(restaurantRepository.findById(restaurantId).orElseThrow());

        return voteRepository.save(vote);
    }
}
