package com.message.job.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.message.common.domin.MessageRecord;
import com.message.common.domin.MessageTaskInfo;
import com.message.common.domin.MessageTaskScheduleConfig;
import com.message.common.enums.MessageTaskInfoStatusEnum;
import com.message.common.mapper.MessageTaskInfoMapper;
import com.message.job.config.MailSenderConfig;
import com.message.job.dispatch.WorkPool;
import com.message.job.service.MessageRecordService;
import com.message.job.service.MessageSendTaskService;
import com.message.job.task.AsyncExecute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageSendTaskServiceImpl implements MessageSendTaskService {

    private final MessageTaskInfoMapper messageTaskInfoMapper;
    private final MessageRecordService messageRecordService;

    private final MessageTaskScheduleConfig config;

    private final WorkPool workPool;

    private final MailSenderConfig senderConfig;

    @Override
    public void processMessageTasks() {
        // 使用配置信息进行任务处理
        int limit = config.getMessageScheduleLimit();
        int maxRetry = config.getMaxRetryNum();

        // 拉取任务
        LambdaQueryWrapper<MessageTaskInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MessageTaskInfo::getStatus, MessageTaskInfoStatusEnum.STATUS_ENUM_NO_SEND.getStatusCode())
                .last("LIMIT " + limit);
        List<MessageTaskInfo> messageTaskInfos = messageTaskInfoMapper.selectList(queryWrapper);


        ArrayList<Future<MessageTaskInfo>> futures = new ArrayList<>();
        // 执行任务
        for (MessageTaskInfo task : messageTaskInfos) {
            Future<MessageTaskInfo> messageTaskInfoFuture = workPool.submitJob(new AsyncExecute(task));
            futures.add(messageTaskInfoFuture);
        }

        // 任务信息刷库
        // 遍历futures列表
        ArrayList<MessageRecord> messageRecords = new ArrayList<>();
        for (Future<MessageTaskInfo> future : futures) {
            try {
                // 获取异步执行的结果，设置最大等待时间为1秒
                MessageTaskInfo messageTaskInfo = future.get();
                MessageRecord messageRecord = new MessageRecord();
                BeanUtils.copyProperties(messageTaskInfo, messageRecord);
                // 将MessageTaskInfo对象添加到新列表中
                messageRecords.add(messageRecord);
            } catch (InterruptedException | ExecutionException e) {
                // 处理异常情况
                log.error("MessageTaskInfo execute error: {}", e.getMessage());
            }
        }
        messageRecordService.saveBatch(messageRecords);
    }


    @Override
    public Boolean addTaskInfo() {
        return null;
    }

}
