package com.message.job.dispatch;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private String port;
    @Value("${spring.data.redis.password}")
    private String passWord;

    /**
     * 单机模式
     *
     * @return
     */
    @Bean
    public Redisson redisson() {
        Config config = new Config();
        // 单机模式
        config.useSingleServer().setAddress("redis://" + host + ":" + port).setPassword(passWord).setDatabase(0);
        return (Redisson) Redisson.create(config);
    }
}
