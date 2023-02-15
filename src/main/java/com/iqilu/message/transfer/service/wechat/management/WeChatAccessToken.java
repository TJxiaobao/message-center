package com.iqilu.message.transfer.service.wechat.management;

import com.aliyun.core.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
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
    private RedisTemplate<String, Serializable> redisTemplate;

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
        String newAccessToken = weChatUserManagement.requestRemoteAccessToken();
        redisTemplate.opsForValue().set(accessTokenRedisKey, newAccessToken, accessTokenDuration, TimeUnit.SECONDS);
    }

}
