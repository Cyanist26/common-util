package com.cuiwq.common.dingtalk.chatbot.model;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static com.cuiwq.common.dingtalk.chatbot.model.ChatbotConstants.MSG_TYPE_KEY;
import static com.cuiwq.common.dingtalk.chatbot.model.ChatbotConstants.MSG_TYPE_LINK;

/**
 * 链接消息
 *
 * @author cuiwq
 * @date 2019-01-22 星期二
 */
@Getter
@Setter
public class LinkMessage implements Message {
    
    /**
     * 消息标题
     */
    private String title;
    
    /**
     * 消息内容。如果太长只会部分展示
     */
    private String text;
    
    /**
     * 点击消息跳转的URL
     */
    private String picUrl;
    
    /**
     * 图片URL
     */
    private String messageUrl;
    
    public LinkMessage(String title, String text, String picUrl, String messageUrl) {
        this.title = title;
        this.text = text;
        this.picUrl = picUrl;
        this.messageUrl = messageUrl;
    }
    
    @Override
    public void verify() {
        if (StringUtils.isBlank(title)) {
            throw new IllegalArgumentException("title should not be blank");
        }
        if (StringUtils.isBlank(messageUrl)){
            throw new IllegalArgumentException("messageUrl should not be blank");
        }
        if (StringUtils.isBlank(text)){
            throw new IllegalArgumentException("text should not be blank");
        }
    }
    
    @Override
    public String toJsonString() {
        verify();
        
        Map<String, Object> items = new HashMap<>();
        items.put(MSG_TYPE_KEY, MSG_TYPE_LINK);
        
        Map<String, String> linkContent = new HashMap<>(4);
        linkContent.put("title", title);
        linkContent.put("messageUrl", messageUrl);
        linkContent.put("text", text);
        if (StringUtils.isNotBlank(picUrl)) {
            linkContent.put("picUrl", picUrl);
        }
        
        items.put("link", linkContent);
        
        return JSON.toJSONString(items);
    }
    
}
