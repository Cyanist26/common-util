package com.cuiwq.common.dingtalk.chatbot.model;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static com.cuiwq.common.dingtalk.chatbot.model.ChatbotConstants.MSG_TYPE_ACTION_CARD;
import static com.cuiwq.common.dingtalk.chatbot.model.ChatbotConstants.MSG_TYPE_KEY;

/**
 * 整体转跳ActionCard消息
 *
 * @author cuiwq
 * @date 2019-01-22 星期二
 */
@Getter
@Setter
public class SingleActionCardMessage extends AbstractActionCardMessage implements Message {
    
    /**
     * 单个按钮标题
     */
    private String singleTitle;
    
    /**
     * 单个按钮转跳URL
     */
    private String singleURL;
    
    public SingleActionCardMessage(String title, String singleTitle, String singleURL) {
        super(title);
        this.singleTitle = singleTitle;
        this.singleURL = singleURL;
    }
    
    @Override
    public void verify() {
        if (StringUtils.isBlank(singleTitle)) {
            throw new IllegalArgumentException("singleTitle should not be blank");
        }
        if (StringUtils.isBlank(singleURL)) {
            throw new IllegalArgumentException("singleURL should not be blank");
        }
    }
    
    @Override
    public String toJsonString() {
        verify();
        
        Map<String, Object> items = new HashMap<>();
        items.put(MSG_TYPE_KEY, MSG_TYPE_ACTION_CARD);
    
        Map<String, Object> content = getContent();
        content.put("singleTitle", singleTitle);
        content.put("singleURL", singleURL);
        items.put("actionCard", content);
        
        return JSON.toJSONString(items);
    }
    
}
