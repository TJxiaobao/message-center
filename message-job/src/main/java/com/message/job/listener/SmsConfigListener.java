package com.message.job.listener;

import com.message.common.domin.SmsConfig;
import com.message.job.config.ReadConfig;
import com.message.job.utils.ConfigMapUtils;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.dromara.sms4j.provider.config.BaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SmsConfigListener {

    @Autowired
    ReadConfig config;

    @EventListener
    public void init(ContextRefreshedEvent event){
        // 创建 SmsBlend 短信实例
        SmsFactory.createSmsBlend(config);
    }
}