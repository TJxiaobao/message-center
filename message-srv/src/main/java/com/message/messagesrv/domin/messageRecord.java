package com.message.messagesrv.domin;

import lombok.Data;

@Data
public class messageRecord {

    private Long id;

    /**
     * 业务Id
     */
    private String bizId;

    /**
     * 业务类型
     */
    private int bizType;

    /**
     * 消息任务 ID
     */
    private String messageTaskId;

    /**
     * 消息类型 0 邮件, 1 阿里云短信, 2 腾讯云短信.....
     */
    private int msgType;

    /**
     * 邮件标题
     */
    private String title;

    /**
     * 邮件内容
     */
    private String Content;

    /**
     * 优先级
     */
    private int priority;

    /**
     * 邮件接受人
     */
    private String receiver;

    /**
     * 邮件状态
     */
    private int status;

    /**
     * 模版ID
     */
    private String templateId;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;
}
