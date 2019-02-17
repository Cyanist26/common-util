package com.cuiwq.common.domain.constant;

import java.time.format.DateTimeFormatter;

/**
 * 日期、数字格式
 *
 * @author cuiwq
 * @date 2019-01-08 星期二
 */
public class FormaterConstant {
    
    public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    public static final String COMPACT_DATETIME_PATTERN = "yyyyMMddHHmmss";
    
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    
    public static final String MONTH_DATE_PATTERN = "MM-dd";
    
    public static final String COMPACT_DATE_PATTERN = "yyyyMMdd";
    
    public static final String SHORTEST_DATE_PATTERN = "yyMMdd";
    
    public static final DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN);
    
    public static final DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
    
    public static final DateTimeFormatter compactDatetimeFormatter = DateTimeFormatter.ofPattern(COMPACT_DATETIME_PATTERN);
    
    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
    
    public static final DateTimeFormatter monthDateFormatter = DateTimeFormatter.ofPattern(MONTH_DATE_PATTERN);
    
    public static final DateTimeFormatter compactDateFormatter = DateTimeFormatter.ofPattern(COMPACT_DATE_PATTERN);
    
    public static final DateTimeFormatter shortestDatetimeFormatter = DateTimeFormatter.ofPattern(SHORTEST_DATE_PATTERN);
    
}
