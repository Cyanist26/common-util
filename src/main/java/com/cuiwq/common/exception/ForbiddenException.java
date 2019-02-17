package com.cuiwq.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 权限不足
 *
 * @author cuiwq
 * @date 2018-11-14 星期三
 */
public class ForbiddenException extends BusinessException {
    
    private String token;
    
    private String url;
    
    public ForbiddenException(String message, String token, String url) {
        super(HttpStatus.FORBIDDEN.value(), message);
        this.token = token;
        this.url = url;
    }
    
    public String getUrl() {
        return url;
    }
    
    public String getToken() {
        return token;
    }
    
    @Override
    public String toString() {
        return "token : " + token + ". " + "url : " + url + ". " + super.toString();
    }
    
}
