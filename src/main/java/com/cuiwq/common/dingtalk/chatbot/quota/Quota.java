package com.cuiwq.common.dingtalk.chatbot.quota;

/**
 * @author cuiwq
 * @date 2019-01-23 星期三
 */
public interface Quota {
    
    void initQuota();
    
    Long getQuota();
    
    void destoryQuota();
    
}
