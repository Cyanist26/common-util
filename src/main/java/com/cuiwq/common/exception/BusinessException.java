package com.cuiwq.common.exception;

import org.springframework.http.HttpStatus;

/**
 * BusinessException 业务异常
 *
 * @author cuiwq
 * @date 2018-10-09 星期二
 */
public class BusinessException extends RuntimeException {
    
    private static final long serialVersionUID = -1070748450068660443L;
    
    /**
     * 响应码
     */
    private int statusCode = HttpStatus.BAD_REQUEST.value();
    
    public BusinessException(String message) {
        super(message);
    }
    
    public BusinessException(Throwable cause) {
        super(cause);
    }
    
    public BusinessException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BusinessException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
    
    @Override
    public String toString() {
        return "statusCode : " + statusCode + ". " + super.toString();
    }
    
}