package com.cuiwq.common.time;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

import static java.time.temporal.ChronoField.*;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @author cuiwq
 * @date 2019-01-24 星期四
 */
public class CustomAdjuster {
    
    public static TemporalAdjuster startOfNextDay() {
        return startOfDayPlus(1L);
    }
    
    public static TemporalAdjuster startOfDayPlus(long days) {
        return (temporal) -> withStartOfToday(temporal).plus(days, DAYS);
    }
    
    public static TemporalAdjuster startOfToday() {
        return CustomAdjuster::withStartOfToday;
    }
    
    public static TemporalAdjuster startOfDayMinus(long days) {
        return (temporal) -> withStartOfToday(temporal).minus(days, DAYS);
    }
    
    public static TemporalAdjuster startOfLastDay() {
        return startOfDayMinus(1L);
    }
    
    private static Temporal withStartOfToday(Temporal temporal) {
        return temporal.with(HOUR_OF_DAY, 0)
                       .with(MINUTE_OF_HOUR, 0)
                       .with(SECOND_OF_MINUTE, 0)
                       .with(NANO_OF_SECOND, 0);
    }
    
}