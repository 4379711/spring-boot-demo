package com.example.demo.utils;

import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


/**
 * @author YaLong
 */
@Component
@ConditionalOnClass(RedissonClient.class)
@ConditionalOnProperty({"spring.redis.host"})
public class RedissonUtil {

    @Autowired
    private RedissonClient redissonClient;

    public void setExpireSecond(String key, String value, long timeToLive) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(value, timeToLive, TimeUnit.SECONDS);
    }

    /**
     * 尝试获取一个锁,一段时间后自动释放锁,允许等待几秒获取
     *
     * @param key         尝试获取某个锁
     * @param waitTime    等待获取锁时间(秒)
     * @param releaseTime 锁自动释放时间(秒)
     * @return 是否获取到锁
     */
    public Boolean autoLock(String key, long waitTime, long releaseTime) {
        RLock lock = redissonClient.getLock(key);
        boolean tryLock;
        try {
            tryLock = lock.tryLock(waitTime, releaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
        return tryLock;
    }

    /**
     * 获取一个锁,一段时间后自动释放锁
     * 注意:获取锁操作阻塞,直到获取到锁
     *
     * @param key         尝试获取某个锁
     * @param releaseTime 锁自动释放时间(秒)
     */
    public void autoLock(String key, long releaseTime) {
        RLock lock = redissonClient.getLock(key);
        lock.lock(releaseTime, TimeUnit.SECONDS);
    }

    /**
     * 尝试获取锁,允许延迟几秒获取
     *
     * @param lock     锁实例
     * @param waitTime 等待获取锁时间(秒)
     * @return 是否获取到锁
     */
    public Boolean tryLock(RLock lock, long waitTime) {
        boolean tryLock;
        try {
            tryLock = lock.tryLock(waitTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
        return tryLock;
    }

    /**
     * 立即尝试获取锁
     *
     * @param lock 锁实例
     * @return 是否获取到锁
     */
    public Boolean tryLock(RLock lock) {
        return this.tryLock(lock, 0);
    }

    /**
     * 创建锁实例
     *
     * @param key 锁名称
     * @return 锁实例
     */
    public RLock createLock(String key) {
        return redissonClient.getLock(key);
    }

    /**
     * 释放锁
     *
     * @param lock 锁实例
     */
    public void unLock(RLock lock) {
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}