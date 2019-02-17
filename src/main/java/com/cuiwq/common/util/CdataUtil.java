package com.cuiwq.common.util;

import com.cuiwq.common.annotation.CDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.lang.reflect.Field;

/**
 * 辅助解析{@link com.cuiwq.common.annotation.CDATA}注解
 *
 * @author cuiwq
 * @date 2019-02-17 星期日
 */
public class CdataUtil {
    
    public static boolean needCDATA(Class<?> targetClass, String fieldAlias) {
        if(existsCDATA(targetClass, fieldAlias)) {
            return true;
        }
        //if cdata is false, scan supperClass until java.lang.Object
        Class<?> superClass = targetClass.getSuperclass();
        while(!superClass.equals(Object.class)) {
            if(existsCDATA(superClass, fieldAlias)) {
                return true;
            }
            superClass = superClass.getSuperclass();
        }
        return false;
    }
    
    public static boolean existsCDATA(Class<?> clazz, String fieldAlias) {
        for(Field field : clazz.getDeclaredFields()) {
            XStreamAlias xStreamAlias = field.getAnnotation(XStreamAlias.class);
            if((xStreamAlias != null && fieldAlias.equals(xStreamAlias.value())) || fieldAlias.equals(field.getName())) {
                if(field.getAnnotation(CDATA.class) != null) {
                    return true;
                }
            }
        }
        return false;
    }
    
}