package com.example.demo.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.common.util.DateUtil.DatePattern;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class DateUtilTest {

    private static final ZoneId Z = ZoneId.of("Asia/Seoul");
    private static final LocalDateTime FIXED = LocalDateTime.of(2025,04,21,10,30,0);
    private static final Clock FIXED_CLOCK =
        Clock.fixed(FIXED.atZone(Z).toInstant(), Z);

    @BeforeEach
    void setup() {
        DateUtil.setClock(FIXED_CLOCK);
    }

    @AfterEach
    void teardown() {
        DateUtil.resetClock();
    }

    @ParameterizedTest
    @EnumSource(DatePattern.class)
    void formatDate_success(DatePattern pattern) {
        // given
        LocalDateTime date = LocalDateTime.of(2025,04,21,23,59,59);
        String expected = date.format(pattern.getFormatter());

        // when
        String actual = DateUtil.formatDate(date, pattern);

        // then
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @EnumSource(DatePattern.class)
    void getToday_Success(DatePattern pattern) {
        // given
        LocalDateTime today = FIXED;
        String expected = today.format(pattern.getFormatter());

        // when
        String actual = DateUtil.getToday(pattern);

        // then
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @EnumSource(DatePattern.class)
    void getYesterday(DatePattern pattern) {
        // given
        LocalDateTime yesterday = FIXED.minusDays(1);
        String expected = yesterday.format(pattern.getFormatter());

        // when
        String actual = DateUtil.getYesterday(pattern);

        // then
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @EnumSource(DatePattern.class)
    void getDateByOffsetFromToday(DatePattern pattern) {
        // given
        int offset = 3;
        LocalDateTime targetDate = FIXED.plusDays(offset);
        String expected = targetDate.format(pattern.getFormatter());

        // when
        String actual = DateUtil.getDateByOffsetFromToday(pattern, offset);


        // then
        assertEquals(expected, actual);
    }
}