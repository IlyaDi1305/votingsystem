package ru.didorenko.votingsystem.web.vote;

import ru.didorenko.votingsystem.MatcherFactory;
import ru.didorenko.votingsystem.to.VoteTo;

import java.util.List;

import static java.time.LocalDate.now;

public class VoteTestData {
    public static final MatcherFactory.Matcher<VoteTo> VOTE_MATCHER = MatcherFactory.usingEqualsComparator(VoteTo.class);

    public static final List<VoteTo> VOTE_TO_1 = List.of(new VoteTo(1, now(), 1, 1));
}