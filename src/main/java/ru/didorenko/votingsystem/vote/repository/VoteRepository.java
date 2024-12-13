package ru.didorenko.votingsystem.vote.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.didorenko.votingsystem.common.BaseRepository;
import ru.didorenko.votingsystem.restaurant.model.Restaurant;
import ru.didorenko.votingsystem.vote.model.Vote;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.user.id =:userId")
    List<Vote> getAllByUserId(int userId);

    Optional<Vote> findByUserIdAndDate(Integer userId, LocalDate date);

    @Transactional
    default void updateAndSave(Vote vote, Restaurant restaurant) {
        vote.setTime(LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
        vote.setRestaurant(restaurant);
        save(vote);
    }
}
