package ru.didorenko.votingsystem.utill;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeUtil {
    private static final Clock clock = Clock.systemDefaultZone();

    public static LocalDate getLocalDate() {
        return LocalDate.now(clock);
    }

    public static LocalTime getCurrentTime() {
        return LocalTime.now(clock);
    }
}