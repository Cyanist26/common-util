package com.cuiwq.common.aspect;

import com.cuiwq.common.annotation.AccessLog;
import com.cuiwq.common.api.AccessLogInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 接口调用情况统计
 *
 * @author cuiwq
 * @date 2019-01-09 星期三
 */
@Slf4j
@Aspect
public class AccessLogAspect<T> {

    private final AccessLogInterceptor<T> accessLogInterceptor;
    
    public AccessLogAspect(AccessLogInterceptor<T> accessLogInterceptor) {
        this.accessLogInterceptor = accessLogInterceptor;
    }
    
    @Pointcut("@annotation(accessLog)")
    public void accessLogPointcut(AccessLog accessLog) {}
    
    @Around("accessLogPointcut(accessLog)")
    public Object log(ProceedingJoinPoint joinPoint, AccessLog accessLog) throws Throwable {
        T logObj = accessLogInterceptor.preHandle(joinPoint, accessLog);
        try {
            Object result = joinPoint.proceed();
            accessLogInterceptor.postHandle(joinPoint, accessLog, result, logObj);
            return result;
        } catch(Throwable e) {
            try {
                accessLogInterceptor.errorHandle(joinPoint, accessLog, e, logObj);
            } catch(Exception e1) {
                log.error("Fail to handle error", e1);
            }
            throw e;
        } finally {
            try {
                accessLogInterceptor.save(logObj);
            } catch(Exception e) {
                log.error("Fail to save access log", e);
            }
        }
    }
    
}
