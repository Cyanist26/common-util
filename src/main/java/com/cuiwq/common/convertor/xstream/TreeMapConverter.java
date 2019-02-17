package com.cuiwq.common.convertor.xstream;

import com.thoughtworks.xstream.converters.Converter;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author cuiwq
 * @date 2019-02-17 星期日
 */
public class TreeMapConverter extends AbstractCustomMapConverter implements Converter {
    
    @Override
    public boolean canConvert(Class clazz) {
        return TreeMap.class.isAssignableFrom(clazz);
    }
    
    @Override
    protected Map<String, String> getMapInstance() {
        return new TreeMap<>();
    }
    
}