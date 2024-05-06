package com.message.job.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.message.common.domin.EmailBlend;
import com.message.common.domin.EmailConfig;
import com.message.common.domin.MessageTaskScheduleConfig;
import com.message.common.domin.SmsConfig;
import com.message.common.mapper.EmailConfigMapper;
import com.message.common.mapper.MessageTaskScheduleConfigMapper;
import com.message.common.mapper.SmsConfigMapper;
import com.message.job.factory.EmailFactory;
import com.message.job.utils.ConfigMapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
public class ConfigInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private final MessageTaskScheduleConfigMapper configMapper;
    private final SmsConfigMapper smsConfigMapper;
    private final EmailConfigMapper mailMapper;

    private final MessageTaskScheduleConfig config;

    public ConfigInitializer(MessageTaskScheduleConfigMapper configMapper, SmsConfigMapper smsConfigMapper, EmailConfigMapper mailMapper) {
        this.configMapper = configMapper;
        this.smsConfigMapper = smsConfigMapper;
        this.mailMapper = mailMapper;
        this.config = new MessageTaskScheduleConfig();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initMessageTaskScheduleConfig();
        initSmsConfig();
        initMailSender();
    }

    @Bean
    public MessageTaskScheduleConfig messageTaskScheduleConfig() {
        return this.config;
    }

    private void initMessageTaskScheduleConfig() {
        // 从数据库中获取配置值
        MessageTaskScheduleConfig dbConfig = configMapper.selectOne(null);

        // 将配置值注入到配置类中
        if (dbConfig != null) {
            config.setMessageScheduleLimit(dbConfig.getMessageScheduleLimit());
            config.setMaxRetryNum(dbConfig.getMaxRetryNum());
            config.setCreateTime(dbConfig.getCreateTime());
            config.setUpdateTime(dbConfig.getUpdateTime());
        }
    }
    private void initSmsConfig() {
        List<SmsConfig> configs = smsConfigMapper.selectList(null);
        Map<String, SmsConfig> map = new HashMap<>();
        for (SmsConfig config : configs) {
            map.put(config.getConfigId(), config);
        }
        ConfigMapUtils.initialize(map);
    }

    private void initMailSender() {
        log.info("初始化mailSender");
//        QueryWrapper<EmailConfig> state = new QueryWrapper<EmailConfig>().eq("state", 1);
        QueryWrapper<EmailConfig> state = new QueryWrapper<EmailConfig>();
        List<EmailConfig> mails = mailMapper.selectList(state);

        if (mails != null && !mails.isEmpty()) {
            mails.forEach(mail -> {
                JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//                javaMailSender.setDefaultEncoding(mail.getDefaultEncoding());
                javaMailSender.setHost(mail.getHost());
                javaMailSender.setPort(mail.getPort());
                javaMailSender.setProtocol(mail.getProtocol());
                javaMailSender.setUsername(mail.getUsername());
                javaMailSender.setPassword(mail.getPassword());
                // 添加数据
                EmailBlend emailBlend = new EmailBlend();
                emailBlend.setConfigId(mail.getConfigId());
                emailBlend.setJavaMailSender(javaMailSender);
                EmailFactory.register(emailBlend);
            });
        } else {
            log.error("数据库无可用的email配置");
        }

    }
}