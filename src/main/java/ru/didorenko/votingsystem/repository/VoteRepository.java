package ru.didorenko.votingsystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.didorenko.votingsystem.common.BaseRepository;
import ru.didorenko.votingsystem.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.user.id =:userId")
    List<Vote> getAllByUserId(int userId);

    Vote findByUserIdAndVoteDate(Integer userId, LocalDate date);
}