package ru.didorenko.votingsystem.validation;

import ru.didorenko.votingsystem.common.error.VoteDeadlineException;
import java.time.LocalTime;

public class VoteValidator {

    private static final LocalTime DEADLINE = LocalTime.of(11, 0);

    public static void validateDeadline() {
        if (LocalTime.now().isAfter(DEADLINE)) {
            throw new VoteDeadlineException("Vote cannot be changed after 11:00 AM.");
        }
    }
}