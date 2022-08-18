package org.calendar.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author xiangqian
 * @date 22:56 2022/08/16
 */
public class DateUtils {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_PATTERN);

    public static LocalDateTime parse(String text) {
        return parse(text, DEFAULT_FORMATTER);
    }

    public static LocalDate format(String text, String pattern) {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime parse(String text, String pattern) {
        return parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime parse(String text, DateTimeFormatter dateTimeFormatter) {
        return LocalDateTime.parse(text, dateTimeFormatter);
    }

    public static String format(LocalDateTime time) {
        return format(time, DEFAULT_FORMATTER);
    }

    public static String format(LocalDateTime time, String pattern) {
        return format(time, DateTimeFormatter.ofPattern(pattern));
    }

    public static String format(LocalDateTime time, DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.format(time);
    }

    public static LocalDateTime timestampToDate(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static long dateToTimestamp(LocalDateTime time) {
        ZoneId zone = ZoneId.systemDefault();
        return time.atZone(zone).toInstant().toEpochMilli();
    }

}
