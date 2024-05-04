package com.message.job.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.message.common.domin.EmailBlend;
import com.message.common.domin.EmailConfig;
import com.message.common.mapper.EmailConfigMapper;
import com.message.job.factory.EmailFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class MailSenderConfig {


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