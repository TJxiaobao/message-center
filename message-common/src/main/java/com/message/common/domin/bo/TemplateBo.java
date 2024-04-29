package com.message.common.domin.bo;

import lombok.Data;

@Data
public class TemplateBo {
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
     * 模版配置
     */
    private String content;

    /**
     * 说明
     */
    private String description;
}
