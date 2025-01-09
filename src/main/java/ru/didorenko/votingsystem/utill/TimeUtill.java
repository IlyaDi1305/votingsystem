package ru.didorenko.votingsystem.utill;

import java.time.Clock;
import java.time.LocalTime;

public class TimeUtill {
    private static final Clock clock = Clock.systemDefaultZone();

    public static LocalTime getCurrentTime() {
        return LocalTime.now(clock);
    }
}
