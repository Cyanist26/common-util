package com.cuiwq.common.dingtalk.chatbot.model;

import com.alibaba.fastjson.JSON;
import com.cuiwq.common.dingtalk.chatbot.MarkdownUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.cuiwq.common.dingtalk.chatbot.model.ChatbotConstants.MSG_TYPE_KEY;
import static com.cuiwq.common.dingtalk.chatbot.model.ChatbotConstants.MSG_TYPE_MARKDOWN;

/**
 * Markdown消息
 *
 * @author cuiwq
 * @date 2019-01-22 星期二
 */
public class MarkdownMessage extends AbstractAtContent implements Message {
    
    /**
     * 首屏会话透出的展示内容
     */
    @Getter
    @Setter
    private String title;
    
    private List<String> items = new ArrayList<>();
    
    public void addItem(String text) {
        items.add(text);
    }
    
    public void addBoldText(String text) {
        items.add(MarkdownUtil.getBoldText(text));
    }
    
    public void addItalicText(String text) {
        items.add(MarkdownUtil.getItalicText(text));
    }
    
    public void addImageText(String text) {
        items.add(MarkdownUtil.getImageText(text));
    }
    
    public void addReferenceText(String text) {
        items.add(MarkdownUtil.getReferenceText(text));
    }
    
    public void addLinkText(String text, String href) {
        items.add(MarkdownUtil.getLinkText(text, href));
    }
    
    public void addHeaderText(int headerType, String text) {
        items.add(MarkdownUtil.getHeaderText(headerType, text));
    }
    
    public void addOrderListText(String... orderItems) {
        items.add(MarkdownUtil.getOrderListText(Arrays.asList(orderItems)));
    }
    
    public void addUnorderListText(String... orderItems) {
        items.add(MarkdownUtil.getUnorderListText(Arrays.asList(orderItems)));
    }
    
    public MarkdownMessage(String title) {
        this.title = title;
    }
    
    @Override
    public void verify() {
        if (StringUtils.isBlank(title)) {
            throw new IllegalArgumentException("title should not be blank");
        }
    }
    
    @Override
    public String toJsonString() {
        verify();
        
        Map<String, Object> result = new HashMap<>(3);
        result.put(MSG_TYPE_KEY, MSG_TYPE_MARKDOWN);
        
        Map<String, Object> markdown = new HashMap<>(2);
        markdown.put("title", title);
        
        StringBuilder markdownText = new StringBuilder();
        for (String item : items) {
            markdownText.append(item).append("\n\n");
        }
        markdown.put("text", markdownText.toString());
        result.put("markdown", markdown);
    
        result.put("at", getAtContent());
        
        return JSON.toJSONString(result);
    }
    
}
