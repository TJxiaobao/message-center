package com.message.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageTypeEnum {

    ALIBABA_SMS(0, "ali_sms", "阿里短信"),

    EMAIL(10, "email", "邮件");

    private final int statusCode;

    private final String statusName;

    private final String statusRemark;
}
