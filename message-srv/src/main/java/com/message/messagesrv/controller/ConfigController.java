package com.message.messagesrv.controller;

import cn.hutool.core.bean.BeanUtil;
import com.message.common.domin.EmailConfig;
import com.message.common.domin.SmsConfig;
import com.message.common.domin.bo.EmailConfigBo;
import com.message.common.domin.bo.SmsConfigBo;
import com.message.common.http.Result;
import com.message.messagesrv.service.EmailConfigService;
import com.message.messagesrv.service.SmsConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/system/config")
public class ConfigController {

    private final EmailConfigService emailConfigService;

    private final SmsConfigService smsConfigService;

    @PostMapping("/add/email")
    public Result<EmailConfig> addEmail(@RequestBody EmailConfigBo emailConfigBo) {
        EmailConfig emailConfig = BeanUtil.copyProperties(emailConfigBo, EmailConfig.class);
        emailConfigService.save(emailConfig);
        return Result.success("success", emailConfig);
    }

    @PostMapping("/add/sms")
    public Result<SmsConfig> addSms(@RequestBody SmsConfigBo smsConfigBo) {
        SmsConfig smsConfig = BeanUtil.copyProperties(smsConfigBo, SmsConfig.class);
        smsConfigService.save(smsConfig);
        return Result.success("success", smsConfig);
    }

    @PostMapping("/update/email")
    public Result<EmailConfig> updateEmail(@RequestBody EmailConfigBo emailConfigBo) {
        EmailConfig emailConfig = BeanUtil.copyProperties(emailConfigBo, EmailConfig.class);
        emailConfigService.save(emailConfig);
        return Result.success("success", emailConfig);
    }

    @PostMapping("/update/sms")
    public Result<SmsConfig> updateEmail(@RequestBody SmsConfigBo smsConfigBo) {
        SmsConfig smsConfig = BeanUtil.copyProperties(smsConfigBo, SmsConfig.class);
        smsConfigService.save(smsConfig);
        return Result.success("success", smsConfig);
    }

    @DeleteMapping("/del/email/{id}")
    public Result<Boolean> delEmail(@PathVariable("id") Long id) {

        emailConfigService.removeById(id);
        return Result.success("success", true);
    }

    @DeleteMapping("/del/sms/{id}")
    public Result<Boolean> delSms(@PathVariable("id") Long id) {
        smsConfigService.removeById(id);
        return Result.success("success", true);
    }
}
