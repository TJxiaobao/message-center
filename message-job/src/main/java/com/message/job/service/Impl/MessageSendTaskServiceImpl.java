package com.message.job.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.message.common.domin.MessageTaskInfo;
import com.message.common.domin.MessageTaskScheduleConfig;
import com.message.common.enums.MessageTaskInfoStatusEnum;
import com.message.common.mapper.MessageTaskInfoMapper;
import com.message.job.config.MailSenderConfig;
import com.message.job.dispatch.WorkPool;
import com.message.job.service.MessageSendTaskService;
import com.message.job.task.AsyncExecute;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageSendTaskServiceImpl implements MessageSendTaskService {

    private final MessageTaskInfoMapper messageTaskInfoMapper;

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

        // 执行任务
        for (MessageTaskInfo task : messageTaskInfos) {
            workPool.executeJob(new AsyncExecute(task));
        }

        // 任务信息刷库
    }

    @Override
    public Boolean addTaskInfo() {
        return null;
    }

    @Override
    public void sendEmail(String configId, MessageTaskInfo messageTaskInfo) {
        JavaMailSenderImpl mailSender = senderConfig.getSender(configId);
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(Objects.requireNonNull(mailSender.getUsername()));
            helper.setTo(messageTaskInfo.getReceiver().split(","));
//            helper.setFrom("");
            helper.setSubject(messageTaskInfo.getTitle());
            helper.setText(messageTaskInfo.getContent(), true);
            mailSender.send(mimeMessage);
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
    }
}
