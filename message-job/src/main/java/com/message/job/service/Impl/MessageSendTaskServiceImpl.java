package com.message.job.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.message.common.domin.MessageRecord;
import com.message.common.domin.MessageTaskInfo;
import com.message.common.domin.MessageTaskScheduleConfig;
import com.message.common.enums.MessageTaskInfoStatusEnum;
import com.message.common.mapper.MessageRecordMapper;
import com.message.common.mapper.MessageTaskInfoMapper;
import com.message.job.dispatch.WorkPool;
import com.message.job.service.MessageSendTaskService;
import com.message.job.task.AsyncExecute;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageSendTaskServiceImpl implements MessageSendTaskService {

    private final MessageTaskInfoMapper messageTaskInfoMapper;

    private final MessageRecordMapper messageRecordMapper;

    private final MessageTaskScheduleConfig config;

    private final WorkPool workPool;

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
        Set<String> collect = messageTaskInfos.stream().map(MessageTaskInfo::getMessageTaskId).collect(Collectors.toSet());
        LambdaQueryWrapper<MessageRecord> messageRecordLambdaQueryWrapper = new LambdaQueryWrapper<>();
        messageRecordLambdaQueryWrapper.like(MessageRecord::getMessageTaskId, collect);
        List<MessageRecord> tasks = messageRecordMapper.selectList(messageRecordLambdaQueryWrapper);

        // 执行任务
        for (MessageRecord task : tasks) {
            workPool.executeJob(new AsyncExecute(task));
        }
    }

    @Override
    public Boolean addTaskInfo() {
        return null;
    }
}
