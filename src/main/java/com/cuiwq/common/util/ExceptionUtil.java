package com.cuiwq.common.util;

import java.sql.DataTruncation;

/**
 * 异常分析工具
 *
 * @author unknown
 * @date 2019-01-08 星期二
 */
public class ExceptionUtil {
    
    /**
     * 从指定的异常对象中获取指定类型的cause异常, 如果不存在则返回null
     * @param e e
     * @param causeExceptionType causeExceptionType
     * @return Exception
     */
    public static Exception getCauseException(Exception e, Class<? extends Exception> causeExceptionType) {
        Throwable t = e;
        while (t != null) {
            if (causeExceptionType.isAssignableFrom(t.getClass())) {
                return (Exception) t;
            }
            t = t.getCause();
        }
        return null;
    }
    
    /**
     * 该异常是否是指定异常类型引起
     * @param e e
     * @param causeExceptionType causeExceptionType
     * @return boolean
     */
    public static boolean isCauseBy(Exception e, Class<? extends Exception> causeExceptionType) {
        return getCauseException(e, causeExceptionType) != null;
    }
    
    /**
     * 异常是否是数据超过数据库字段长度导致
     * @param e e
     * @return boolean
     */
    public static boolean isCauseByDataTruncation(Exception e) {
        return isCauseBy(e, DataTruncation.class);
    }
    
}
