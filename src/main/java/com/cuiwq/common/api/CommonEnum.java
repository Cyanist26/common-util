package com.cuiwq.common.api;

/**
 * Type通用接口
 *
 * @author cuiwq
 * @date 2019-01-08 星期二
 */
public interface CommonEnum<E extends Enum<E>> {
    
    int getCode();
    
    String getName();
    
    E getTypeByCode(int code);
    
}
