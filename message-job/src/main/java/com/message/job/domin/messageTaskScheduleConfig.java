package com.message.job.domin;

import lombok.Data;

/**
 * 调度配置信息
 */
@Data
public class messageTaskScheduleConfig {

    private Long id;

    /**
     * 每次拉取任务数量
     */
    private int messageScheduleLimit;

    /**
     * 最大重试次数
     */
    private int maxRetryNum;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;
}
