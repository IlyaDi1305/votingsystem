package ru.didorenko.votingsystem.validation;

import java.time.LocalTime;

public class VoteValidator {

    private static final LocalTime DEADLINE = LocalTime.of(11, 0);

    public static void validateDeadline() {
        if (LocalTime.now().isAfter(DEADLINE)) {
            throw new IllegalStateException("Vote cannot be changed after 11:00 AM.");
        }
    }
}