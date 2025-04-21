package com.example.demo.common.util;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

public class DateUtil {

    private static Clock clock = Clock.systemDefaultZone();

    private DateUtil() {}

    // 테스트 시 고정된 Clock 주입을 위함
    public static void setClock(Clock fixedClock) {
        clock = fixedClock;
    }

    public static void resetClock() {
        clock = Clock.systemDefaultZone();
    }

    public static String getToday(DatePattern pattern) {
        return formatDate(LocalDateTime.now(clock), pattern);
    }

    public static String getYesterday(DatePattern pattern) {
        return formatDate(LocalDateTime.now(clock).minusDays(1), pattern);
    }

    public static String getDateByOffsetFromToday(DatePattern pattern, int days) {
        return formatDate(LocalDateTime.now(clock).plusDays(days), pattern);
    }

    public static String formatDate(LocalDateTime date, DatePattern pattern) {
        return date.format(pattern.getFormatter());
    }

    @Getter
    public enum DatePattern {

        YYYYMMMDD("yyyyMMdd"),
        YYYY_MM_DD("yyyy-MM-dd"),
        YYYYMMMDD_HHMMSS("yyyyMMddHHmmss"),
        YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss");

        private final DateTimeFormatter formatter;

        DatePattern(String pattern) {
            this.formatter = DateTimeFormatter.ofPattern(pattern);
        }
    }
}
