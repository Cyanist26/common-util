package com.cuiwq.common.api;

import com.cuiwq.common.annotation.AccessLog;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author cuiwq
 * @date 2019-01-09 星期三
 */
public interface AccessLogInterceptor<T> {

    default T preHandle(ProceedingJoinPoint joinPoint, AccessLog accessLog) {
        return null;
    }
    
    default void postHandle(ProceedingJoinPoint joinPoint, AccessLog accessLog, Object result, T log) {}
    
    default void errorHandle(ProceedingJoinPoint joinPoint, AccessLog accessLog, Throwable e, T log) {}
    
    void save(T log);
    
}
