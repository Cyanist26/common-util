package com.cuiwq.common.dingtalk.chatbot.quota;

import com.cuiwq.common.dingtalk.chatbot.ChatbotClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author cuiwq
 * @date 2019-01-23 星期三
 */
@Slf4j
public class RedisQuota implements Quota {
    
    public static final String REDIS_KEY_PREFIX = "chatbot::";
    
    private static final String LOCK_KEY = REDIS_KEY_PREFIX + "resetLock";
    
    private final String REDIS_KEY;
    
    private RedisTemplate<String, Integer> redisTemplate;
    
    private ScheduledExecutorService scheduler;
    
    public RedisQuota(String token, RedisTemplate<String, Integer> redisTemplate) {
        this.REDIS_KEY = REDIS_KEY_PREFIX + token;
        this.redisTemplate = redisTemplate;
    
        /* 重置Quota定时任务 */
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::resetQuota,
                Duration.between(LocalDateTime.now(), LocalDateTime.now().withSecond(0).withNano(0).plusMinutes(3)).getSeconds(),
                120L, TimeUnit.SECONDS);
    }
    
    @Override
    public void initQuota() {
//        redisTemplate.boundValueOps(REDIS_KEY).setIfAbsent(MAX_QUOTA, Duration.ofSeconds(61));
        BoundValueOperations<String, Integer> operations = redisTemplate.boundValueOps(REDIS_KEY);
        if(operations.get() == null) {
            operations.set(0, 121L, TimeUnit.SECONDS);
        }
        log.info("RedisQuota init successfully.");
    }
    
    @Override
    public Long getQuota() {
        Long usedQuota = redisTemplate.opsForValue().increment(REDIS_KEY, 1L);
        while(usedQuota == null || usedQuota > ChatbotClient.MAX_QUOTA) {
            try {
                TimeUnit.SECONDS.sleep(10L);
            } catch(InterruptedException e) {
                log.warn("wait send quota error", e);
            }
            usedQuota = redisTemplate.opsForValue().increment(REDIS_KEY, 1L);
        }
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch(InterruptedException e) {
            log.warn("wait send error", e);
        }
        return usedQuota;
    }
    
    @Override
    public void destoryQuota() {
        scheduler.shutdown();
    }
    
    private void resetQuota() {
        BoundValueOperations<String, Integer> lockOperations = redisTemplate.boundValueOps(LOCK_KEY);
        Boolean absent = lockOperations.setIfAbsent(0);
        if(absent != null && absent) {
            lockOperations.expire(2L, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(REDIS_KEY, 0, 121L, TimeUnit.SECONDS);
        }
    }
    
}
