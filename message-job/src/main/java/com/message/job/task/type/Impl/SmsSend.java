package com.message.job.task.type.Impl;

import com.message.common.domin.MessageTaskInfo;
import com.message.common.enums.MessageTaskInfoStatusEnum;
import com.message.common.enums.MessageTypeEnum;
import com.message.job.task.type.AbstractSend;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;

public class SmsSend extends AbstractSend {

    @Override
    public void send(MessageTaskInfo messageTaskInfo, String configId) {
        int crtRetryNum = messageTaskInfo.getCrtRetryNum();
        for (int i = 1; i <= messageTaskInfo.getCrtRetryNum(); i++) {
            SmsBlend smsBlend = SmsFactory.getSmsBlend(configId);
            SmsResponse smsResponse = smsBlend.sendMessage(messageTaskInfo.getReceiver(), messageTaskInfo.getContent());
            crtRetryNum++;
            if (smsResponse.isSuccess()) {
                messageTaskInfo.setCrtRetryNum(crtRetryNum);
                messageTaskInfo.setStatus(MessageTaskInfoStatusEnum.STATUS_ENUM_SEND_SUCCESS.getStatusCode());
                return;
            }
        }
        messageTaskInfo.setCrtRetryNum(crtRetryNum);
        messageTaskInfo.setStatus(MessageTaskInfoStatusEnum.STATUS_ENUM_SEND_FAIL.getStatusCode());
    }

    @Override
    public String supportType() {
        return MessageTypeEnum.SMS.getStatusName();
    }
}
