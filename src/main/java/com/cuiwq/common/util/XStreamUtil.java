package com.cuiwq.common.util;

import com.cuiwq.common.convertor.xstream.HashMapConverter;
import com.cuiwq.common.convertor.xstream.LinkedHashMapConverter;
import com.cuiwq.common.convertor.xstream.TreeMapConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Writer;
import java.util.*;

/**
 * XStream工具类
 *
 * @author cuiwq
 * @date 2019-02-17 星期日
 */
@Slf4j
public class XStreamUtil {
    
    private static final String ROOT_ALIAS = "xml";
    
    private static final String CDATA_START = "<![CDATA[";
    
    private static final String CDATA_END = "]]>";
    
    private static final XStream INSTANCE;
    
    private static final XStream COMPRESS_INSTANCE;
    
    private static final XStream CDATA_INSTANCE;
    
    private static final XStream COMPRESS_CDATA_INSTANCE;
    
    private static final XStream SELETIVE_CDATA_INSTANCE;
    
    private static final XStream COMPRESS_SELETIVE_CDATA_INSTANCE;
    
    static {
        INSTANCE = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        initInstance(INSTANCE);
    
        COMPRESS_INSTANCE = new XStream(new XppDriver(new XmlFriendlyNameCoder("-_", "_")) {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new CompactWriter(out, getNameCoder());
            }
        });
        initInstance(COMPRESS_INSTANCE);
    
        CDATA_INSTANCE = new XStream(new XppDriver(new XmlFriendlyNameCoder("-_", "_")) {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out, getNameCoder()) {
                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        writer.write(CDATA_START);
                        writer.write(text);
                        writer.write(CDATA_END);
                    }
                };
            }
        });
        initInstance(CDATA_INSTANCE);
    
        COMPRESS_CDATA_INSTANCE = new XStream(new XppDriver(new XmlFriendlyNameCoder("-_", "_")) {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new CompactWriter(out, getNameCoder()) {
                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        writer.write(CDATA_START);
                        writer.write(text);
                        writer.write(CDATA_END);
                    }
                };
            }
        });
        initInstance(COMPRESS_CDATA_INSTANCE);
    
        SELETIVE_CDATA_INSTANCE = new XStream(new XppDriver(new XmlFriendlyNameCoder("-_", "_")) {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out, getNameCoder()) {
                    
                    boolean isCdata = false;
    
                    Class<?> targetClass = null;
    
                    @Override
                    public void startNode(String name, Class clazz) {
                        super.startNode(name, clazz);
                        if(targetClass == null) {
                            targetClass = clazz;
                        }
                        isCdata = CdataUtil.needCDATA(targetClass, name);
                    }
                    
                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if(isCdata) {
                            writer.write(CDATA_START);
                            writer.write(text);
                            writer.write(CDATA_END);
                        } else {
                            super.writeText(writer, text);
                        }
                    }
                };
            }
        });
        initInstance(SELETIVE_CDATA_INSTANCE);
    
        COMPRESS_SELETIVE_CDATA_INSTANCE = new XStream(new XppDriver(new XmlFriendlyNameCoder("-_", "_")) {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new CompactWriter(out, getNameCoder()) {
    
                    boolean isCdata = false;
    
                    Class<?> targetClass = null;
    
                    @Override
                    public void startNode(String name, Class clazz) {
                        super.startNode(name, clazz);
                        if(targetClass == null) {
                            targetClass = clazz;
                        }
                        isCdata = CdataUtil.needCDATA(targetClass, name);
                    }
                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if(isCdata) {
                            writer.write(CDATA_START);
                            writer.write(text);
                            writer.write(CDATA_END);
                        } else {
                            super.writeText(writer, text);
                        }
                    }
                };
            }
        });
        initInstance(COMPRESS_SELETIVE_CDATA_INSTANCE);
        
        log.info("init XStreamUtil success");
    }
    
    /**
     * 以压缩的方式输出XML
     *
     * @return xml
     */
    public static String toCompressXml(Object obj) {
        return toXML(selectInstance(SerializationType.COMPRESS), obj);
    }
    
    /**
     * 以压缩的方式输出带CDATA的XML
     *
     * @return xml
     */
    public static String toCompressXmlWithCData(Object obj) {
        return toXML(selectInstance(SerializationType.COMPRESS_CDATA), obj);
    }
    
    /**
     * 以格式化的方式输出XML
     *
     * @return xml
     */
    public static String toXml(Object obj) {
        return toXML(selectInstance(SerializationType.PRETTY), obj);
    }
    
    /**
     * 以格式化的方式输出带CDATA的XML
     *
     * @return xml
     */
    public static String toXmlWithCData(Object obj) {
        return toXML(selectInstance(SerializationType.PRETTY_CDATA), obj);
    }
    
    /**
     * xml转换成JavaBean
     *
     * @return bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBean(String xml, Class<T> clazz) {
        // 识别cls类中的注解
        INSTANCE.processAnnotations(clazz);
        // 设置JavaBean的类别名
        INSTANCE.aliasType(ROOT_ALIAS, clazz);
        return (T) INSTANCE.fromXML(xml);
    }
    
    private static void initInstance(XStream instance) {
        XStream.setupDefaultSecurity(instance);
        instance.ignoreUnknownElements();
        registerConverter(instance);
        processAlias(instance);
    }
    
    private static void registerConverter(XStream instance) {
        instance.registerConverter(new HashMapConverter());
        instance.registerConverter(new LinkedHashMapConverter());
        instance.registerConverter(new TreeMapConverter());
    }
    
    private static void processAlias(XStream instance) {
        instance.alias(ROOT_ALIAS, Map.class);
        instance.alias(ROOT_ALIAS, List.class);
        instance.alias(ROOT_ALIAS, Set.class);
        instance.alias(ROOT_ALIAS, SortedSet.class);
        instance.alias(ROOT_ALIAS, Calendar.class);
    }
    
    private static XStream selectInstance(SerializationType type) {
        switch(type) {
            case COMPRESS:
                return COMPRESS_INSTANCE;
            case PRETTY_CDATA:
                return CDATA_INSTANCE;
            case COMPRESS_CDATA:
                return COMPRESS_CDATA_INSTANCE;
            case PRETTY:
            default:
                return INSTANCE;
        }
    }
    
    private static String toXML(XStream instance, Object obj) {
        // 识别obj类中的注解
        instance.processAnnotations(obj.getClass());
        // 设置JavaBean的类别名
        instance.alias(ROOT_ALIAS, obj.getClass());
        return instance.toXML(obj);
    }
    
    private enum SerializationType {
        PRETTY,
        COMPRESS,
        PRETTY_CDATA,
        COMPRESS_CDATA
    }
    
}