package com.message.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageTypeEnum {

    SMS(0, "sms", "短信"),

    EMAIL(10, "email", "邮件");

    private final int statusCode;

    private final String statusName;

    private final String statusRemark;
}
