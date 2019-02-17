package com.cuiwq.common.dingtalk.chatbot.model;

import com.cuiwq.common.dingtalk.chatbot.MarkdownUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static com.cuiwq.common.dingtalk.chatbot.model.ChatbotConstants.HIDE_AVATAR_ON;

/**
 * @author cuiwq
 * @date 2019-01-22 星期二
 */
@Getter
@Setter
public class AbstractActionCardMessage {
    
    /**
     * 首屏会话透出的展示内容
     */
    private String title;
    
    /**
     * banner图片URL
     */
    private String bannerUrl;
    
    /**
     * 文字标题
     */
    private String briefTitle;
    
    /**
     * 文字内容
     */
    private String briefText;
    
    /**
     * 0-正常发消息者头像,1-隐藏发消息者头像
     */
    private boolean hideAvatar;
    
    public AbstractActionCardMessage(String title) {
        this.title = title;
    }
    
    private void verify() {
        if (StringUtils.isBlank(title)) {
            throw new IllegalArgumentException("title should not be blank");
        }
    }
    
    public Map<String, Object> getContent() {
        verify();
        
        Map<String, Object> content = new HashMap<>();
        content.put("title", title);
        StringBuilder text = new StringBuilder();
        if (StringUtils.isNotBlank(bannerUrl)) {
            text.append(MarkdownUtil.getImageText(bannerUrl)).append("\n");
        }
        if (StringUtils.isNotBlank(briefTitle)) {
            text.append(MarkdownUtil.getHeaderText(3, briefTitle)).append("\n");
        }
        if (StringUtils.isNotBlank(briefText)) {
            text.append(briefText).append("\n");
        }
        content.put("text", text.toString());
        if (hideAvatar) {
            content.put("hideAvatar", HIDE_AVATAR_ON);
        }
        return content;
    }
    
}
