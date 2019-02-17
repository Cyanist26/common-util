package com.cuiwq.common.dingtalk.chatbot.model;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cuiwq.common.dingtalk.chatbot.model.ChatbotConstants.MSG_TYPE_FEED_CARD;
import static com.cuiwq.common.dingtalk.chatbot.model.ChatbotConstants.MSG_TYPE_KEY;

/**
 * FeedCard消息
 *
 * @author cuiwq
 * @date 2019-01-22 星期二
 */
public class FeedCardMessage implements Message {
    
    private List<FeedCardMessageItem> feedItems = new ArrayList<>();
    
    public void addFeedItems(String title, String pidURL, String messageURL) {
        feedItems.add(new FeedCardMessageItem(title, pidURL, messageURL));
    }
    
    @Override
    public void verify() {
        if (feedItems == null || feedItems.isEmpty()) {
            throw new IllegalArgumentException("feedItems should not be null or empty");
        }
        for (FeedCardMessageItem item : feedItems) {
            if (StringUtils.isBlank(item.getTitle())) {
                throw new IllegalArgumentException("title should not be blank");
            }
            if (StringUtils.isBlank(item.getMessageURL())) {
                throw new IllegalArgumentException("messageURL should not be blank");
            }
            if (StringUtils.isBlank(item.getPicURL())) {
                throw new IllegalArgumentException("picURL should not be blank");
            }
        }
    }
    
    @Override
    public String toJsonString() {
        verify();
        
        Map<String, Object> items = new HashMap<>();
        items.put(MSG_TYPE_KEY, MSG_TYPE_FEED_CARD);
        
        Map<String, Object> feedCard = new HashMap<>();
        feedCard.put("links", feedItems);
        items.put("feedCard", feedCard);
        
        return JSON.toJSONString(items);
    }
    
    /**
     * FeedCard消息项
     *
     * @author cuiwq
     * @date 2019-01-22 星期二
     */
    @Getter
    @Setter
    @AllArgsConstructor
    private class FeedCardMessageItem {
        
        /**
         * 单条信息文本
         */
        private String title;
        
        /**
         * 单条信息后面图片的URL
         */
        private String picURL;
        
        /**
         * 点击单条信息到跳转链接
         */
        private String messageURL;
        
    }
    
}
