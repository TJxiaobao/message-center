package com.message.job.domin.bo;

import lombok.Data;

@Data
public class MessageTaskInfoBo {


    /**
     * 消息任务 ID
     */
    private String messageTaskId;

    /**
     * 消息任务类型
     */
    private String  messageTaskType;

    /**
     * 任务状态 0 未发送, 1 发送中, 2 发送成功, 3 发送失败
     */
    private int status;

    /**
     * 最大重试次数
     */
    private int maxRetryNum;

    /**
     * 已经重试次数
     */
    private int crtRetryNum;

    /**
     * 重试间隔
     */
    private int retryInterval;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;
}
