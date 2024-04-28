package com.message.common.domin.bo;

import lombok.Data;

import java.util.List;

@Data
public class MessageTaskInfoBo {


    /**
     * message 标题
     */
    private String title;

    /**
     * message 内容
     */
    private String Content;

    /**
     * message 接受人
     */
    private List<String> receiver;

    /**
     * 模版 ID (sms 需要选择)
     */
    private String configId;

    /**
     * 消息任务类型
     */
    private List<String> messageType;


    /**
     * 最大重试次数 (可选)
     */
    private int maxRetryNum;

    /**
     * 重试间隔（可选）
     */
    private int retryInterval;

}
