package com.cuiwq.common;

import com.cuiwq.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * 基于Redis的锁
 *
 * @author cuiwq
 * @date 2018-10-22 星期一
 */
@Slf4j
public class RedisLock {
    
    /**
     * redis key 前缀
     */
    private static final String LOCK_KEY_PREFIX = "lock::";
    
    /**
     * redis cache value
     */
    private static final String LOCK_OBJ = "l";
    
    /**
     * 默认加锁超时时间：1s
     */
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(1);
    
    /**
     * 等待加锁重试等待时间
     */
    private static final long RETRY_INTERVAL = 100;
    
    /**
     * 等待加锁默认重试次数
     */
    private static final int DEFAULT_TIMES_OF_RETRY = 10;
    
    private StringRedisTemplate redisTemplate;
    
    public RedisLock(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    /**
     *  获取特定key的锁，使用默认加锁时间{@link #DEFAULT_TIMEOUT}
     *
     * @param key 锁的key
     * @return 获取锁成功：true<br/>
     *                 获取锁失败：false
     */
    public boolean getLock(String key) {
        return getLock(key, DEFAULT_TIMEOUT);
    }
    
    /**
     *  获取特定key的锁
     *
     * @param key 锁的key
     * @param timeout 锁的超时时间
     * @return 获取锁成功：true<br/>
     *                 获取锁失败：false
     */
    public boolean getLock(String key, Duration timeout) {
        BoundValueOperations<String, String> operations = redisTemplate.boundValueOps(LOCK_KEY_PREFIX + key);
        Boolean absent = operations.setIfAbsent(LOCK_OBJ);
        if (absent != null && absent) {
            operations.expire(timeout.toMillis(), TimeUnit.MILLISECONDS);
            return true;
        }
        return false;
    }
    
    /**
     * 等待锁，超过默认重试次数{@link #DEFAULT_TIMES_OF_RETRY}后抛出异常，使用默认加锁时间{@link #DEFAULT_TIMEOUT}
     *
     * @param key 锁的key
     * @throws BusinessException 等待超时抛出该异常
     */
    public void waitLock(String key) {
        waitLock(key, DEFAULT_TIMEOUT);
    }
    
    /**
     * 等待锁，超过默认重试次数{@link #DEFAULT_TIMES_OF_RETRY}后抛出异常
     *
     * @param key key 锁的key
     * @param timeout 锁的超时时间
     * @throws BusinessException 等待超时抛出异常
     */
    public void waitLock(String key, Duration timeout) {
        waitLock(key, timeout, DEFAULT_TIMES_OF_RETRY);
    }
    
    /**
     * 等待锁，超过重试次数后抛出异常
     *
     * @param key key 锁的key
     * @param timeout 锁的超时时间
     * @param timesOfRetry 重试次数
     * @throws BusinessException 等待超时抛出异常
     */
    public void waitLock(String key, Duration timeout, int timesOfRetry) {
        long end = System.currentTimeMillis() + timesOfRetry * RETRY_INTERVAL;
        while(!getLock(key, timeout)) {
            if(System.currentTimeMillis() > end) {
                log.warn("等待锁超时 [key = {}][timeout = {}][timesOfRetry = {}]", key, timeout, timesOfRetry);
                throw new BusinessException("等待锁超时");
            }
            try {
                TimeUnit.MILLISECONDS.sleep(RETRY_INTERVAL);
            } catch(InterruptedException e) {
                log.error("等待加锁，线程休眠异常", e);
            }
        }
    }
    
    /**
     * 释放锁
     * @param key 锁的key
     */
    public void unlock(String key) {
        redisTemplate.delete(LOCK_KEY_PREFIX + key);
    }
    
}
