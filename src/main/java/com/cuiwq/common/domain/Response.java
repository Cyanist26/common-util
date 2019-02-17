package com.cuiwq.common.domain;

/**
 * 通用响应数据
 *
 * @author cuiwq
 * @date 2018-09-14 星期五
 */
public class Response<D> {
    
    public Response() {
    
    }
    
    public Response(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
    
    public Response(int statusCode, String message, D data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
    
    /**
     * 响应码
     */
    private int statusCode;
    
    /**
     * 响应描述
     */
    private String message;
    
    /**
     * 响应业务数据
     */
    private D data;
    
    public int getStatusCode() {
        return statusCode;
    }
    
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public D getData() {
        return data;
    }
    
    public void setData(D data) {
        this.data = data;
    }
    
}
