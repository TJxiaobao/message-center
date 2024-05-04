package com.message.job.task.type;

import com.message.common.domin.MessageTaskInfo;

public abstract class AbstractSend {

    public abstract void send(MessageTaskInfo messageTaskInfo, String configId);

    public abstract String supportType();
}
