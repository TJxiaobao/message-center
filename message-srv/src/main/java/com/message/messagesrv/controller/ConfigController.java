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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "系统配置")
public class ConfigController {

    private final EmailConfigService emailConfigService;

    private final SmsConfigService smsConfigService;

    @ApiOperation("添加邮箱配置")
    @PostMapping("/add/email")
    public Result<EmailConfig> addEmail(@ApiParam(value = "邮件配置类", required = true) @RequestBody EmailConfigInsertBo emailConfigInsertBo) {
        EmailConfig emailConfig = BeanUtil.copyProperties(emailConfigInsertBo, EmailConfig.class);
        emailConfigService.save(emailConfig);
        return Result.success("success", emailConfig);
    }

    @ApiOperation("添加短信配置")
    @PostMapping("/add/sms")
    public Result<SmsConfig> addSms(@RequestBody SmsConfigInsertBo smsConfigInsertBo) {
        SmsConfig smsConfig = BeanUtil.copyProperties(smsConfigInsertBo, SmsConfig.class);
        smsConfigService.save(smsConfig);
        return Result.success("success", smsConfig);
    }

    @ApiOperation("更新邮箱配置")
    @PostMapping("/update/email")
    public Result<EmailConfig> updateEmail(@RequestBody EmailConfigUpdateBo emailConfigBo) {
        EmailConfig emailConfig = BeanUtil.copyProperties(emailConfigBo, EmailConfig.class);
        emailConfigService.updateById(emailConfig);
        return Result.success("success", emailConfig);
    }

    @ApiOperation("更新短信配置")
    @PostMapping("/update/sms")
    public Result<SmsConfig> updateEmail(@RequestBody SmsConfigUpdateBo smsConfigBo) {
        SmsConfig smsConfig = BeanUtil.copyProperties(smsConfigBo, SmsConfig.class);
        smsConfigService.updateById(smsConfig);
        return Result.success("success", smsConfig);
    }

    @ApiOperation("删除邮箱配置")
    @DeleteMapping("/del/email")
    public Result<Boolean> delEmail(@RequestParam("id") String id) {

        emailConfigService.removeById(Long.parseLong(id));
        return Result.success("success", true);
    }

    @ApiOperation("删除短信配置")
    @DeleteMapping("/del/sms")
    public Result<Boolean> delSms(@RequestParam("id") String id) {
        smsConfigService.removeById(Long.parseLong(id));
        return Result.success("success", true);
    }
}
