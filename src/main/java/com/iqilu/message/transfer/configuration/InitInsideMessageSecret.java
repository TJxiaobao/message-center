package com.iqilu.message.transfer.configuration;

import com.iqilu.message.transfer.dao.AppSecretDao;
import com.iqilu.message.transfer.pojo.AppSecret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 卢斌
 */
@Component
public class InitInsideMessageSecret implements ApplicationRunner {


    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Value("${socket.inside-secret-key}")
    private String appSecretKey;


    @Autowired
    private AppSecretDao appSecretDao;

    @Override
    public void run(ApplicationArguments args) {
        List<AppSecret> secrets = appSecretDao.listAllAppSecrets();
        Map<String, AppSecret> mapParam = new HashMap<>();
        if (! CollectionUtils.isEmpty(secrets)) {
            for (AppSecret appSecret : secrets) {
                mapParam.put(appSecret.getAppId(), appSecret);
            }
        }
        redisTemplate.opsForHash().putAll(appSecretKey, mapParam);
    }

}
