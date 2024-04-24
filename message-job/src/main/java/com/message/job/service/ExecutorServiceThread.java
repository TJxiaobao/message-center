package com.message.job.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.message.job.domin.messageTaskInfo;
import com.message.job.utils.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Slf4j
@DependsOn("ApplicationContextUtil")
public class ExecutorServiceThread implements Runnable {

    private final ExecutorService executorService = (ExecutorService) ApplicationContextUtil.getBean("virtualThreadTaskExecutor");
    private String taskType;

    @Override
    public void run() {
        pullAndDo(taskType);
    }

    public ExecutorServiceThread() {
    }

    public ExecutorServiceThread(String taskType) {
        this.taskType = taskType;
    }

    /**
     */
    public void pullAndDo(String taskType) {
        try {
            //TODO 之后通过配置类读取management的地址来源。采用多management多taskExecutor的方式
            //TODO 1、从数据库拉取任务信息

            // 2.把数据解析成任务列表

            // 3. 获取任务id列表批量任务进度(status)更改已提交为进行中

            // 4. 通过线程来执行任务
            ArrayList<messageTaskInfo> messageTaskInfos = new ArrayList<>();
            doTask(taskType, messageTaskInfos);
            // 5. 任务都执行完，更改任务状态为成功默认0，失败1，成功2

        } catch (Exception  e) {
            throw new RuntimeException(e);
        }
    }

    private void doTask(String taskType, List<messageTaskInfo> messageTaskInfos)  {
        // todo 做类型判断，然后发送不同类型的消息
    }

    public void email1_0(List<messageTaskInfo> messageTaskInfos){
        for (messageTaskInfo messageTaskInfo : messageTaskInfos) {
            executorService.submit();
        }
    }

    public void sms_ali(List<messageTaskInfo> messageTaskInfos) {
        for (messageTaskInfo messageTaskInfo : messageTaskInfos) {
            executorService.submit();
        }
    }
}