package com.cuiwq.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cuiwq
 * @create 2018-12-22 星期六
 */
@Slf4j
public class JacksonUtil {
    
    private static ObjectMapper mapper;
    
    public static void init(ObjectMapper mapper) {
        JacksonUtil.mapper = mapper;
    }
    
    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch(JsonProcessingException e) {
            log.error("json serialization error", e);
        }
        return null;
    }
    
    public static String toPrettyJson(Object obj) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch(JsonProcessingException e) {
            log.error("pretty json serialization error", e);
        }
        return null;
    }
    
    public static JsonNode toNode(Object obj) {
        return mapper.valueToTree(obj);
    }
    
    public static <T> T parse(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch(Exception e) {
            log.error("json parse error [json = {}][class = {}][msg = {}]", json, clazz.getName(), e.getMessage());
        }
        return null;
    }
    
    public static <T> T parse(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch(Exception e) {
            log.error("json parse error [json = {}][class = {}][msg = {}]", json, type.getClass().getName(), e.getMessage());
        }
        return null;
    }
    
    public static <T> List<T> parseList(String json, Class<T> clazz) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
        try {
            return mapper.readValue(json, javaType);
        } catch(IOException e) {
            log.error("json list parse error [json = {}][class = {}][msg = {}]", json, clazz.getName(), e.getMessage());
        }
        return null;
    }
    
    public static JsonNode readTree(String json) {
        try {
            return mapper.readTree(json);
        } catch(IOException e) {
            log.error("json parse error [json = {}][msg = {}]", json, e.getMessage());
        }
        return null;
    }
    
}