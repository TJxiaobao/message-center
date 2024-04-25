package com.message.job.task;

import com.message.common.domin.MessageRecord;
import com.message.common.enums.MessageTaskInfoStatusEnum;
import com.message.common.enums.MessageTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;

@Slf4j
public class AsyncExecute implements Runnable {

    private MessageRecord messageRecord;

    public AsyncExecute() {
    }

    public AsyncExecute(MessageRecord messageRecord) {
        this.messageRecord = messageRecord;
    }

    @Override
    public void run() {
        try {
            log.info("执行信息类型为" + messageRecord.getMsgType() + "任务id" + messageRecord.getMessageTaskId());
            send(messageRecord);
        } catch (Exception e) {
            log.error("id为" + messageRecord.getMessageTaskId() + "的任务执行失败,抛出异常" + e);
            throw new RuntimeException(e);
        }
    }

    public void send(MessageRecord messageRecord) {
        // todo 实现发送业务
        if (MessageTypeEnum.ALIBABA_SMS.getStatusCode() == messageRecord.getMsgType()) {
            sendSms(MessageTypeEnum.ALIBABA_SMS.getStatusName());
        } else if (MessageTypeEnum.EMAIL.getStatusCode() == messageRecord.getMsgType()) {
            // 发送邮件业务
        }
        // todo 更多消息业务
    }

    public void sendSms(String configId) {
        SmsBlend smsBlend = SmsFactory.getSmsBlend(configId);
        SmsResponse smsResponse = smsBlend.sendMessage(messageRecord.getReceiver(),messageRecord.getContent());
        if (smsResponse.isSuccess()) {
            messageRecord.setStatus(MessageTaskInfoStatusEnum.STATUS_ENUM_SEND_SUCCESS.getStatusCode());
        }
    }
}
