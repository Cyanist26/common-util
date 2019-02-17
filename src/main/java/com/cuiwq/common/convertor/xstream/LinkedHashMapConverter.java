package com.cuiwq.common.convertor.xstream;

import com.thoughtworks.xstream.converters.Converter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author cuiwq
 * @date 2019-02-17 星期日
 */
public class LinkedHashMapConverter extends AbstractCustomMapConverter implements Converter {
    
    @Override
    public boolean canConvert(Class clazz) {
        return LinkedHashMap.class.isAssignableFrom(clazz);
    }
    
    @Override
    protected Map<String, String> getMapInstance() {
        return new LinkedHashMap<>();
    }
    
}