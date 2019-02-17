package com.cuiwq.common.convertor.spring;

import com.cuiwq.common.api.CommonEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 自定义枚举类转换器Factory
 *
 * @author cuiwq
 * @date 2018-10-09 星期二
 */
@Slf4j
public class CommonEnumConverterFactory<E extends Enum<E> & CommonEnum<E>> implements ConverterFactory<String, CommonEnum<E>> {
    
    private static final Map<Class, Converter> converterMap = new WeakHashMap<>();
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends CommonEnum<E>> Converter<String, T> getConverter(Class<T> targetType) {
        Converter convertor = converterMap.get(targetType);
        if(convertor == null) {
            convertor = new CommonEnumConverter<>((Class<E>) targetType);
            converterMap.put(targetType, convertor);
        }
        return convertor;
    }
    
}
