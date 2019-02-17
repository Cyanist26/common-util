package com.cuiwq.common.dingtalk.chatbot.model;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cuiwq.common.dingtalk.chatbot.model.ChatbotConstants.*;

/**
 * 独立转跳ActionCard消息
 *
 * @author cuiwq
 * @date 2019-01-22 星期二
 */
public class MultiActionCardMessage extends AbstractActionCardMessage implements Message {
    
    /**
     * 按钮排布样式
     */
    private ActionButtonStyle actionButtonStyle = ActionButtonStyle.VERTICAL;
    
    /**
     * 按钮
     */
    private List<ActionCardAction> actions = new ArrayList<>(MAX_ACTION_BUTTON_CNT);
    
    public MultiActionCardMessage(String title) {
        super(title);
    }
    
    public ActionButtonStyle getActionButtonStyle() {
        return actionButtonStyle;
    }
    
    public void setHorizontalButtonStyle() {
        this.actionButtonStyle = ActionButtonStyle.HORIZONTAL;
    }
    
    public void setVerticalButtonStyle() {
        this.actionButtonStyle = ActionButtonStyle.VERTICAL;
    }
    
    public void addAction(String title, String actionURL) {
        actions.add(new ActionCardAction(title, actionURL));
    }
    
    @Override
    public void verify() {
        if (actions.size() < MIN_ACTION_BUTTON_CNT) {
            throw new IllegalArgumentException("number of actions can't less than " + MIN_ACTION_BUTTON_CNT);
        }
        if (actions.size() > MAX_ACTION_BUTTON_CNT) {
            throw new IllegalArgumentException("number of actions can't more than " + MAX_ACTION_BUTTON_CNT);
        }
    }
    
    @Override
    public String toJsonString() {
        verify();
        
        Map<String, Object> items = new HashMap<>();
        items.put(MSG_TYPE_KEY, MSG_TYPE_ACTION_CARD);
    
        Map<String, Object> content = getContent();
        content.put("btns", actions);
        if (actions.size() == 2 && actionButtonStyle == ActionButtonStyle.HORIZONTAL) {
            content.put("btnOrientation", "1");
        }
        items.put("actionCard", content);
        
        return JSON.toJSONString(items);
    }
    
    /**
     * ActionCard按钮
     *
     * @author cuiwq
     * @date 2019-01-22 星期二
     */
    @Getter
    @Setter
    @AllArgsConstructor
    private class ActionCardAction {
        
        /**
         * 按钮标题
         */
        private String title;
        
        /**
         * 按钮链接
         */
        private String actionURL;
        
    }
    
    /**
     *  按钮样式
     *
     * @author cuiwq
     * @date 2019-01-22 星期二
     */
    private enum ActionButtonStyle {
        /**
         * 水平
         */
        HORIZONTAL,
        /**
         * 垂直
         */
        VERTICAL
    }
    
}
