package com.cuiwq.common.dingtalk.chatbot.quota;

import com.cuiwq.common.dingtalk.chatbot.ChatbotClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 滑动时间窗口
 *
 * @author cuiwq
 * @create 2019-01-23 星期三
 */
@Slf4j
@SuppressWarnings("unchecked")
public class SlidingWindowQuota implements Quota {
    
    public static final String REDIS_KEY_PREFIX = "chatbot::";
    
    private final String REDIS_KEY;
    
    private RedisTemplate<String, Long> redisTemplate;
    
    public SlidingWindowQuota(String token, RedisTemplate<String, Long> redisTemplate) {
        this.REDIS_KEY = REDIS_KEY_PREFIX + token;
        this.redisTemplate = redisTemplate;
    }
    
    @Override
    public void initQuota() {
        long now = System.currentTimeMillis();
        BoundListOperations<String, Long> operations = redisTemplate.boundListOps(REDIS_KEY);
        Long start = operations.index(0);
        while(start != null && now - start > 60000L) {
            operations.leftPop();
            start = operations.index(0);
        }
        log.info("SlidingWindowQuota init successfully.");
    }
    
    @Override
    public Long getQuota() {
        long now = System.currentTimeMillis();
        BoundListOperations<String, Long> operations = redisTemplate.boundListOps(REDIS_KEY);
        if(operations.size() == 0) {
            operations.rightPush(now);
            return operations.size();
        }
        if(now - operations.index(0) > 60000L) {
            operations.leftPop();
        }
        
        while(operations.size() >= ChatbotClient.MAX_QUOTA) {
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch(InterruptedException e) {
                log.warn("Wait send quota error", e);
            }
    
            if((now = System.currentTimeMillis()) - operations.index(0) > 60000L) {
                operations.leftPop();
            }
        }
        operations.rightPush(now);
        
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch(InterruptedException e) {
            log.warn("Wait send error.", e);
        }
        return operations.size();
    }
    
    @Override
    public void destoryQuota() {
    
    }
    
}