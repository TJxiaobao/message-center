package com.message.messagesrv.domin;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "message_template")
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
