package com.cuiwq.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leon Cheng
 */
@Slf4j
public class HttpRequestUtil {
    
    public static final HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }
    
    public static final String getRequestIp() {
        HttpServletRequest request = getRequest();
        if(request == null) {
            return null;
        }
        String ip = request.getHeader("x-forwarded-for");
        
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = getRequest().getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    public static String getHeaderOrParamValue(String name) {
        return getHeaderOrParamValue(getRequest(), name);
    }
    
    public static String getHeaderOrParamValue(HttpServletRequest request, String name) {
        if (request == null) {
            return null;
        }
        String value = request.getHeader(name);
        if(StringUtils.isNotEmpty(value)) {
            return value;
        } else {
            return request.getParameter(name);
        }
    }
    
}
