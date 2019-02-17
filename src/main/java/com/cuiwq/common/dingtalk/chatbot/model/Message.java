package com.cuiwq.common.dingtalk.chatbot.model;

/**
 * 消息接口
 *
 * @author SDK
 * @date 2019-01-22 星期二
 */
public interface Message {
    
    /**
     * 返回消息的Json格式字符串
     *
     * @return 消息的Json格式字符串
     */
    String toJsonString();
    
    /**
     * 校验消息，不通过则抛出异常
     */
    void verify();
    
}
