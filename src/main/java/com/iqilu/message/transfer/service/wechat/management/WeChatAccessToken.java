package com.iqilu.message.transfer.service.wechat.management;

import com.aliyun.core.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author 卢斌
 */
@Log4j2
@Component
public class WeChatAccessToken {

    @Autowired
    private WeChatUserManagement weChatUserManagement;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Value("${chat.redis-lock.access-token}")
    private String accessTokenLockKey;

    @Value("${chat.redis-key.access-token.value}")
    private String accessTokenRedisKey;

    /**
     * accessToken有效时间
     */
    @Value("${chat.access-token.failure-time}")
    private Integer accessTokenDuration;


    /**
     * 定时刷新accessToken.
     *
     *
     */
    @Scheduled(fixedDelayString = "${chat.access-token.flush-millisecond}")
    public void refreshWechatAccessToken() {
        RLock lock = redissonClient.getLock(accessTokenLockKey);
        try {
            boolean isLocked = lock.tryLock(1, 8, TimeUnit.SECONDS);
            if (isLocked) {
                String newAccessToken = weChatUserManagement.requestRemoteAccessToken();
                redisTemplate.opsForValue().set(accessTokenRedisKey, newAccessToken, accessTokenDuration, TimeUnit.SECONDS);
            }
        } catch (InterruptedException interruptedException) {
            log.warn("获取锁发生异常");
            interruptedException.printStackTrace();
        } finally {
            if (lock != null && lock.isLocked() && lock.isHeldByCurrentThread()) {
                try {
                    lock.unlock();
                } catch (Exception e) {
                    log.error("lock.unlock()解锁异常 -> ", e);
                }
            }
        }
    }

}
