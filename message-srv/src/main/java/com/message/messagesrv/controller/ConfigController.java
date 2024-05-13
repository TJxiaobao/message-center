package com.message.messagesrv.controller;

import cn.hutool.core.bean.BeanUtil;
import com.message.common.domin.EmailConfig;
import com.message.common.domin.SmsConfig;
import com.message.common.domin.bo.EmailConfigInsertBo;
import com.message.common.domin.bo.EmailConfigUpdateBo;
import com.message.common.domin.bo.SmsConfigInsertBo;
import com.message.common.domin.bo.SmsConfigUpdateBo;
import com.message.common.http.Result;
import com.message.common.service.EmailConfigService;
import com.message.common.service.SmsConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/system/config")
public class ConfigController {

    private final EmailConfigService emailConfigService;

    private final SmsConfigService smsConfigService;

    @PostMapping("/add/email")
    public Result<EmailConfig> addEmail(@RequestBody EmailConfigInsertBo emailConfigInsertBo) {
        EmailConfig emailConfig = BeanUtil.copyProperties(emailConfigInsertBo, EmailConfig.class);
        emailConfigService.save(emailConfig);
        return Result.success("success", emailConfig);
    }

    @PostMapping("/add/sms")
    public Result<SmsConfig> addSms(@RequestBody SmsConfigInsertBo smsConfigInsertBo) {
        SmsConfig smsConfig = BeanUtil.copyProperties(smsConfigInsertBo, SmsConfig.class);
        smsConfigService.save(smsConfig);
        return Result.success("success", smsConfig);
    }

    @PostMapping("/update/email")
    public Result<EmailConfig> updateEmail(@RequestBody EmailConfigUpdateBo emailConfigBo) {
        EmailConfig emailConfig = BeanUtil.copyProperties(emailConfigBo, EmailConfig.class);
        emailConfigService.updateById(emailConfig);
        return Result.success("success", emailConfig);
    }

    @PostMapping("/update/sms")
    public Result<SmsConfig> updateEmail(@RequestBody SmsConfigUpdateBo smsConfigBo) {
        SmsConfig smsConfig = BeanUtil.copyProperties(smsConfigBo, SmsConfig.class);
        smsConfigService.updateById(smsConfig);
        return Result.success("success", smsConfig);
    }

    @DeleteMapping("/del/email")
    public Result<Boolean> delEmail(@RequestParam("id") String id) {

        emailConfigService.removeById(Long.parseLong(id));
        return Result.success("success", true);
    }

    @DeleteMapping("/del/sms")
    public Result<Boolean> delSms(@RequestParam("id") String id) {
        smsConfigService.removeById(Long.parseLong(id));
        return Result.success("success", true);
    }
}
