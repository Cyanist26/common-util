package com.cuiwq.common.annotation;

import com.cuiwq.common.domain.type.AccessLogMethodType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记需要记录请求耗时及相关信息的方法
 *
 * @author cuiwq
 * @date 2018-09-09 星期日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLog {
    
    /**
     * 自定义附加信息，将记录到数据库中
     * @return 附加信息
     */
    String value() default "";
    
    /**
     * 方法类型
     * @return 方法类型
     */
    AccessLogMethodType type() default AccessLogMethodType.UNKNOWN;
    
    /**
     * 是否记录请求参数
     * 默认为 否
     * @return boolean
     */
    boolean recordParams() default false;
    
    /**
     * 是否记录返回值
     * 默认为 否
     * @return boolean
     */
    boolean recordResult() default false;
    
}