package com.cuiwq.common.convertor.spring;

import com.cuiwq.common.api.CommonEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

/**
 * @author cuiwq
 * @date 2018-10-09 星期二
 */
@Slf4j
public class CommonEnumConverter<E extends Enum<E> & CommonEnum<E>> implements Converter<String, E> {
    
    private final E type;
    
    CommonEnumConverter(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        E[] types = type.getEnumConstants();
        if (types == null || types.length == 0) {
            log.error("{} does not represent an enum type.", type.getSimpleName());
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }
        this.type = types[0];
    }
    
    @Override
    public E convert(String source) {
        int code;
        try {
            code = Integer.parseInt(source);
        } catch(NumberFormatException e) {
            log.debug("枚举类[{}]参数转换出错，参数[{}]无法转换为整形，返回null", type.getClass().getSimpleName(), source);
            return null;
        }
        return type.getTypeByCode(code);
    }
    
}
