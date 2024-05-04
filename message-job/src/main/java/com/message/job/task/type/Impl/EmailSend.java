package com.message.job.task.type.Impl;

import com.message.common.domin.MessageTaskInfo;
import com.message.common.enums.MessageTypeEnum;
import com.message.job.task.type.AbstractSend;

public class EmailSend extends AbstractSend {

    @Override
    public void send(MessageTaskInfo messageTaskInfo, String configId) {
        // todo
    }

    @Override
    public String supportType() {
        return MessageTypeEnum.EMAIL.getStatusName();
    }
}
