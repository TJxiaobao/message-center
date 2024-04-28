package com.message.job.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.message.common.domin.EmailConfig;
import com.message.common.mapper.EmailConfigMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@AllArgsConstructor
public class MailSenderConfig {

    private final Map<String, JavaMailSenderImpl> emailConfigMap = new ConcurrentHashMap<>();

    private final EmailConfigMapper mailMapper;


    public void buildMailSender() {
        log.info("初始化mailSender");
        List<EmailConfig> mails = mailMapper.selectList(new QueryWrapper<EmailConfig>().eq("state", 1));

        if (mails != null && !mails.isEmpty()) {
            mails.forEach(mail -> {
                JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
                javaMailSender.setDefaultEncoding(mail.getDefaultEncoding());
                javaMailSender.setHost(mail.getHost());
                javaMailSender.setPort(mail.getPort());
                javaMailSender.setProtocol(mail.getProtocol());
                javaMailSender.setUsername(mail.getUsername());
                javaMailSender.setPassword(mail.getPassword());
                // 添加数据
                emailConfigMap.put(mail.getConfigId(), javaMailSender);
            });
        } else {
            log.error("数据库无可用的email配置");
        }

    }

    public JavaMailSenderImpl getSender(String config) {
        if (emailConfigMap.isEmpty()) {
            buildMailSender();
        }
        // 随机返回一个JavaMailSender
        return emailConfigMap.get(config);
    }

    public void clear() {
        emailConfigMap.clear();
    }
}