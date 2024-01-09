package ru.savelyev.votingsystem.util;

import lombok.experimental.UtilityClass;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class TimeLimitUtil {
    public static final String TIME_PATTERN = "HH:mm";
    public static final DateTimeFormatter TIME_LIMIT_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);
    public static LocalTime timeOver = LocalTime.of(11, 0);

    public static LocalTime getLimitTime() {
        return timeOver;
    }

    public static void setLimitTime(LocalTime timeOver) {
        TimeLimitUtil.timeOver = timeOver;
    }
}
