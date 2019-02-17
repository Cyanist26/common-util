package com.cuiwq.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 无效授权信息
 *
 * @author cuiwq
 * @date 2018-11-02 星期五
 */
public class AuthorizationException extends BusinessException {
    
    private String token;
    
    public AuthorizationException(String message, String token) {
        super(HttpStatus.UNAUTHORIZED.value(), message);
        this.token = token;
    }
    
    public String getToken() {
        return token;
    }
    
    @Override
    public String toString() {
        return "token : " + token + ". " + super.toString();
    }
    
}
