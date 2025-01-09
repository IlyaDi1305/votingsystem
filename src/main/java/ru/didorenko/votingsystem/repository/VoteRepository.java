package ru.didorenko.votingsystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.didorenko.votingsystem.common.BaseRepository;
import ru.didorenko.votingsystem.common.error.NotFoundException;
import ru.didorenko.votingsystem.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.user.id =:userId")
    List<Vote> getAllByUserId(int userId);

    default List<Vote> getExistedByUserId(int userId) {
        List<Vote> votes = getAllByUserId(userId);
        if (votes.isEmpty()) {
            throw new NotFoundException("No votes found for user with id=" + userId);
        }
        return votes;
    }

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId AND v.voteDate = :date")
    Vote findByUserIdAndVoteDate(Integer userId,LocalDate date);

    default Vote getExistedByUserIdAndVoteDate(Integer userId, LocalDate date) {
        Vote vote = findByUserIdAndVoteDate(userId, date);
        if (vote == null) {
            throw new NotFoundException("Vote not found for user with id=" + userId + " and date=" + date);
        }
        return vote;
    }
}