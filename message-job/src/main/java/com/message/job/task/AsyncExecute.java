package com.message.job.task;

import com.message.common.domin.MessageTaskInfo;
import com.message.common.enums.MessageTaskInfoStatusEnum;
import com.message.common.enums.MessageTypeEnum;
import com.message.job.service.MessageSendTaskService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

@Slf4j
@Component
public class AsyncExecute implements Callable<MessageTaskInfo> {

    private MessageTaskInfo messageTaskInfo;
    private MessageSendTaskService messageSendTaskService;

    public AsyncExecute() {
    }

    public AsyncExecute(MessageTaskInfo messageTaskInfo) {
        this.messageTaskInfo = messageTaskInfo;
    }

    @Override
    public MessageTaskInfo call() throws Exception {
        try {
            log.info("执行信息类型为" + messageTaskInfo.getMsgTaskType() + "任务id" + messageTaskInfo.getId());
            return send(messageTaskInfo);
        } catch (Exception e) {
            log.error("id为" + messageTaskInfo.getId() + "的任务执行失败,抛出异常" + e);
            throw new RuntimeException(e);
        }
    }

    public MessageTaskInfo send(MessageTaskInfo messageTaskInfo) {
        // todo 实现发送业务
        messageTaskInfo.setCrtRetryNum(messageTaskInfo.getCrtRetryNum() + 1);
        messageTaskInfo.setStatus(MessageTaskInfoStatusEnum.STATUS_ENUM_SENDING.getStatusCode());
        if (MessageTypeEnum.SMS.getStatusCode() == messageTaskInfo.getMsgTaskType()) {
            sendSms(messageTaskInfo.getConfigId());
        } else if (MessageTypeEnum.EMAIL.getStatusCode() == messageTaskInfo.getMsgTaskType()) {
            // 发送邮件业务
            sendEmail();
        }
        // todo 更多消息业务
        return messageTaskInfo;
    }

    private void sendSms(String configId) {
        int crtRetryNum = messageTaskInfo.getCrtRetryNum();
        for (int i = 1; i <= messageTaskInfo.getCrtRetryNum(); i++) {
            SmsBlend smsBlend = SmsFactory.getSmsBlend(configId);
            SmsResponse smsResponse = smsBlend.sendMessage(messageTaskInfo.getReceiver(), messageTaskInfo.getContent());
            crtRetryNum++;
            if (smsResponse.isSuccess()) {
                // todo 计算重试了基础然后刷库 （是否先放到一个List里面，然后进行一个统一刷库）
                messageTaskInfo.setCrtRetryNum(crtRetryNum);
                messageTaskInfo.setStatus(MessageTaskInfoStatusEnum.STATUS_ENUM_SEND_SUCCESS.getStatusCode());
                return;
            }
        }
        messageTaskInfo.setCrtRetryNum(crtRetryNum);
        messageTaskInfo.setStatus(MessageTaskInfoStatusEnum.STATUS_ENUM_SEND_FAIL.getStatusCode());

    }

    private void sendEmail() {
        int crtRetryNum = messageTaskInfo.getCrtRetryNum();
        for (int i = 1; i <= messageTaskInfo.getCrtRetryNum(); i++) {
            Boolean status = messageSendTaskService.sendEmail(messageTaskInfo.getConfigId(), messageTaskInfo);
            crtRetryNum++;
            //2.通过实例进行消息发送
            if (status) {
                // todo 计算重试了基础然后刷库 （是否先放到一个List里面，然后进行一个统一刷库）
                messageTaskInfo.setCrtRetryNum(crtRetryNum);
                messageTaskInfo.setStatus(MessageTaskInfoStatusEnum.STATUS_ENUM_SEND_SUCCESS.getStatusCode());
                return;
            }
        }
        messageTaskInfo.setCrtRetryNum(crtRetryNum);
        messageTaskInfo.setStatus(MessageTaskInfoStatusEnum.STATUS_ENUM_SEND_FAIL.getStatusCode());
    }

}
