package org.asst.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author xiangqian
 * @date 16:17 2022/08/27
 */
@Slf4j
public class DateUtilsTest {

    @Test
    public void testLocalDateTime() {
        long timestamp = DateUtils.dateToTimestamp(LocalDateTime.now());
        log.debug("timestamp: {}", timestamp);

        LocalDateTime date = DateUtils.timestampToLocalDateTime(timestamp);
        log.debug("date: {}", date);
    }

    @Test
    public void testLocalDate() {
        long timestamp = DateUtils.dateToTimestamp(LocalDate.now());
        log.debug("timestamp: {}", timestamp);

        LocalDate date = DateUtils.timestampToLocalDate(timestamp);
        log.debug("date: {}", date);
    }

}
