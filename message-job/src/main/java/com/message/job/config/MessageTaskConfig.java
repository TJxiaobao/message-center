package com.message.job.config;

import com.message.common.domin.MessageTaskScheduleConfig;
import com.message.common.mapper.MessageTaskScheduleConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageTaskConfig {

    @Autowired
    private MessageTaskScheduleConfigMapper configMapper;

    @Bean("messageTaskScheduleConfig")
    public MessageTaskScheduleConfig messageTaskScheduleConfig() {
        MessageTaskScheduleConfig messageTaskScheduleConfig = configMapper.selectOne(null);
        return messageTaskScheduleConfig;
    }

}
