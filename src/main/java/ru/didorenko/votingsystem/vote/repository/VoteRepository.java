package ru.didorenko.votingsystem.vote.repository;

import org.springframework.stereotype.Repository;
import ru.didorenko.votingsystem.common.BaseRepository;
import ru.didorenko.votingsystem.vote.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface VoteRepository extends BaseRepository<Vote> {
    Optional<Vote> findByUserIdAndDate(Integer userId, LocalDate date);
}