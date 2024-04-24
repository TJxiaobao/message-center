package com.message.messagesrv.domin;

import lombok.Data;

@Data
public class messageTemplate {

    private Long id;

    /**
     * 模版 ID
     */
    private String messageTemplateId;

    /**
     * 模版名称
     */
    private String templateName;

    /**
     * 状态 0 启用 2 禁用
     */
    private int status;

    /**
     * 模版类型
     */
    private int type;

    /**
     * 说明
     */
    private String description;
}
