package com.message.job.config;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

/**
 * 调度配置信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "message_task_schedule_config")
@Configuration
public class MessageTaskScheduleConfig {


    /**
     * 每次拉取任务数量
     */
    private int messageScheduleLimit;

    /**
     * 最大重试次数
     */
    private int maxRetryNum;

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
