package ru.didorenko.votingsystem.web.vote;

import ru.didorenko.votingsystem.MatcherFactory;
import ru.didorenko.votingsystem.to.VoteTo;

import static java.time.LocalDate.now;

public class VoteTestData {
    public static final MatcherFactory.Matcher<VoteTo> VOTE_MATCHER = MatcherFactory.usingEqualsComparator(VoteTo.class);

    public static final VoteTo voteTo1 = new VoteTo(1, now(), 1);
}