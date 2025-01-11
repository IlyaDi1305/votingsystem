package ru.didorenko.votingsystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.didorenko.votingsystem.common.error.DuplicateVoteException;
import ru.didorenko.votingsystem.common.error.NotFoundException;
import ru.didorenko.votingsystem.model.User;
import ru.didorenko.votingsystem.model.Vote;
import ru.didorenko.votingsystem.repository.RestaurantRepository;
import ru.didorenko.votingsystem.repository.VoteRepository;
import ru.didorenko.votingsystem.to.VoteTo;
import ru.didorenko.votingsystem.utill.VoteUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.didorenko.votingsystem.utill.DateTimeUtil.getCurrentTime;
import static ru.didorenko.votingsystem.utill.DateTimeUtil.getLocalDate;
import static ru.didorenko.votingsystem.utill.VoteUtil.createTo;
import static ru.didorenko.votingsystem.utill.VoteUtil.createToList;
import static ru.didorenko.votingsystem.validation.VoteValidator.validateDeadline;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    private final RestaurantRepository restaurantRepository;

    public VoteTo getExistedById(int id, int userId) {
        Vote vote = voteRepository.getExisted(id);
        if (vote.getUser().getId() != userId) {
            throw new IllegalArgumentException("Access denied: the vote does not belong to the current user.");
        }
        return createTo(vote);
    }

    public List<VoteTo> getAllByUserId(int userId) {
        List<Vote> votes = voteRepository.getExistedByUserId(userId);
        return createToList(votes);
    }

    @Transactional
    public Vote createVote(int restaurantId, User user) {
        LocalDate today = getLocalDate();
        LocalTime currentTime = getCurrentTime();
        if (voteRepository.findByUserIdAndVoteDate(user.id(), today) != null) {
            throw new DuplicateVoteException("Vote already exists for today. Use PUT to update.");
        }
        try {
            return voteRepository.save(new Vote
                    (user, restaurantRepository.getReferenceById(restaurantId), today, currentTime));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("Restaurant with id " + restaurantId + " does not exist.");
        }
    }

    @Transactional
    public void updateVote(int restaurantId, User user) {
        LocalDate today = LocalDate.now();
        LocalTime todayTime = getCurrentTime();
        validateDeadline(todayTime);
        Vote vote = voteRepository.getExistedByUserIdAndVoteDate(user.id(), today);
        try {
            voteRepository.save(VoteUtil.setVote
                    (vote, restaurantRepository.getReferenceById(restaurantId), todayTime, user));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("Restaurant with id " + restaurantId + " does not exist");
        }
    }
}