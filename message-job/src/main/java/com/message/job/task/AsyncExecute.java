package com.message.job.task;

import com.message.common.domin.MessageTaskInfo;
import com.message.common.domin.bo.EmailInfoBo;
import com.message.common.enums.MessageTypeEnum;
import com.message.job.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AsyncExecute implements Runnable {

    private MessageTaskInfo messageTaskInfo;
    @Autowired
    private EmailService emailService;

    public AsyncExecute() {
    }

    public AsyncExecute(MessageTaskInfo messageTaskInfo) {
        this.messageTaskInfo = messageTaskInfo;
    }

    @Override
    public void run() {
        try {
            log.info("执行信息类型为" + messageTaskInfo.getMsgTaskType() + "任务id" + messageTaskInfo.getId());
            send(messageTaskInfo);
        } catch (Exception e) {
            log.error("id为" + messageTaskInfo.getId() + "的任务执行失败,抛出异常" + e);
            throw new RuntimeException(e);
        }
    }

    public void send(MessageTaskInfo messageTaskInfo) {
        // todo 实现发送业务
        if (MessageTypeEnum.SMS.getStatusCode() == messageTaskInfo.getMsgTaskType()) {
            sendSms(messageTaskInfo.getConfigId());
        } else if (MessageTypeEnum.EMAIL.getStatusCode() == messageTaskInfo.getMsgTaskType()) {
            // 发送邮件业务
            sendEmail();
        }
        // todo 更多消息业务
    }

    private void sendSms(String configId) {
        for (int i = 1; i <= messageTaskInfo.getCrtRetryNum(); i++) {
            SmsBlend smsBlend = SmsFactory.getSmsBlend(configId);
            SmsResponse smsResponse = smsBlend.sendMessage(messageTaskInfo.getReceiver(), messageTaskInfo.getContent());
            if (smsResponse.isSuccess()) {
                // todo 计算重试了基础然后刷库 （是否先放到一个List里面，然后进行一个统一刷库）
                break;
            }
        }
    }

    private void sendEmail() {
        // todo 实现邮件发送
        for (int i = 1; i <= messageTaskInfo.getCrtRetryNum(); i++) {
            emailService.sendEmail(messageTaskInfo.getConfigId(), messageTaskInfo);
            //2.通过实例进行消息发送
            if (true) {
                // todo 计算重试了基础然后刷库 （是否先放到一个List里面，然后进行一个统一刷库）
                break;
            }
        }
    }

}
