package com.cuiwq.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author cuiwq
 * @date 2019-01-08 星期二
 */
public class DateTimeUtil {
    
    public static Date transfer(LocalDateTime localDateTime) {
        return transfer(localDateTime, ZoneId.systemDefault());
    }
    
    public static Date transfer(LocalDateTime localDateTime, ZoneId zoneId) {
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }
    
    public static LocalDateTime transfer(Date date) {
        return transfer(date, ZoneId.systemDefault());
    }
    
    public static LocalDateTime transfer(Date date, ZoneId zoneId) {
        return LocalDateTime.ofInstant(date.toInstant(), zoneId);
    }
    
}
