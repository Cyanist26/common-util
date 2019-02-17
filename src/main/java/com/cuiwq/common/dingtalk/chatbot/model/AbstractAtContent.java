package com.cuiwq.common.dingtalk.chatbot.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cuiwq
 * @date 2019-01-22 星期二
 */
public abstract class AbstractAtContent {
    
    /**
     * 被@人的手机号
     */
    private List<String> atMobiles;
    
    /**
     * @ 所有人时:true,否则为:false
     */
    private boolean isAtAll;
    
    public List<String> getAtMobiles() {
        return atMobiles;
    }
    
    public void setAtMobiles(List<String> atMobiles) {
        this.atMobiles = atMobiles;
    }
    
    public void addAtMobiles(String phone) {
        if(atMobiles == null) {
            atMobiles = new ArrayList<>();
        }
        atMobiles.add(phone);
    }
    
    public boolean isAtAll() {
        return isAtAll;
    }
    
    public void setIsAtAll(boolean isAtAll) {
        this.isAtAll = isAtAll;
    }
    
    public Map<String, Object> getAtContent() {
        Map<String, Object> content = new HashMap<>();
        if (atMobiles != null && !atMobiles.isEmpty()) {
            content.put("atMobiles", atMobiles);
        }
        if (isAtAll) {
            content.put("isAtAll", isAtAll);
        }
        return content;
    }
    
}
