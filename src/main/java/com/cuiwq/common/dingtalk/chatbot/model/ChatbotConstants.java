package com.cuiwq.common.dingtalk.chatbot.model;

/**
 * 机器人常量
 *
 * @author cuiwq
 * @date 2019-01-22 星期二
 */
public class ChatbotConstants {
    
    static final String MSG_TYPE_KEY = "msgtype";
    
    static final String MSG_TYPE_TEXT = "text";
    
    static final String MSG_TYPE_LINK = "link";
    
    static final String MSG_TYPE_MARKDOWN = "markdown";
    
    static final String MSG_TYPE_ACTION_CARD = "actionCard";
    
    static final String MSG_TYPE_FEED_CARD = "feedCard";
    
    /**
     * 最大按钮数量
     */
    static final int MAX_ACTION_BUTTON_CNT = 4;
    
    /**
     * 最小按钮数量
     */
    static final int MIN_ACTION_BUTTON_CNT = 1;
    
    public static final int MIN_HEADER_TYPE = 1;
    
    public static final int MAX_HEADER_TYPE = 6;
    
    static final String HIDE_AVATAR_ON = "1";
    
    static final String HIDE_AVATAR_OFF = "0";
    
}
