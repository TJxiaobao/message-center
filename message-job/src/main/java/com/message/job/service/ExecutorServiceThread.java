package com.message.job.service;

import com.message.job.utils.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;

@Slf4j
@DependsOn("ApplicationContextUtil")
public class ExecutorServiceThread implements Runnable {

    private final MessageSendTaskService messageSendTaskService = (MessageSendTaskService) ApplicationContextUtil.getBean("MessageSendTaskService");


    @Override
    public void run() {
        messageSendTaskService.processMessageTasks();
    }

}
