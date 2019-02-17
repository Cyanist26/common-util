package com.cuiwq.common.dingtalk.chatbot.model;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static com.cuiwq.common.dingtalk.chatbot.model.ChatbotConstants.MSG_TYPE_KEY;
import static com.cuiwq.common.dingtalk.chatbot.model.ChatbotConstants.MSG_TYPE_TEXT;

/**
 * 文本消息
 *
 * @author cuiwq
 * @date 2019-01-22 星期二
 */
@Getter
@Setter
public class TextMessage extends AbstractAtContent implements Message {
    
    /**
     * 消息内容
     */
    private String text;
    
    public TextMessage(String text) {
        this.text = text;
    }
    
    @Override
    public void verify() {
        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException("text should not be blank");
        }
    }
    
    @Override
    public String toJsonString() {
        verify();
        
        Map<String, Object> items = new HashMap<>(3);
        items.put(MSG_TYPE_KEY, MSG_TYPE_TEXT);
        
        Map<String, String> textContent = new HashMap<>(1);
        textContent.put("content", text);
        items.put("text", textContent);
        items.put("at", getAtContent());
        
        return JSON.toJSONString(items);
    }
    
}
