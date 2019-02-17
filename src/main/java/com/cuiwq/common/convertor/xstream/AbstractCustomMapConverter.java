package com.cuiwq.common.convertor.xstream;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.Map;

/**
 * XStream Map转换器
 *
 * @author cuiwq
 * @date 2019-02-17 星期日
 */
public abstract class AbstractCustomMapConverter implements Converter {
    
    @Override
    public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
        Map map = (Map) value;
        for(Object obj : map.entrySet()) {
            Map.Entry entry = (Map.Entry) obj;
            writer.startNode(entry.getKey().toString());
            Object val = entry.getValue();
            if(null != val) {
                writer.setValue(val.toString());
            }
            writer.endNode();
        }
    }
    
    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        Map<String, String> map = getMapInstance();
        while(reader.hasMoreChildren()) {
            reader.moveDown();
            map.put(reader.getNodeName(), reader.getValue());
            reader.moveUp();
        }
        return map;
    }
    
    protected abstract Map<String, String> getMapInstance();
    
}