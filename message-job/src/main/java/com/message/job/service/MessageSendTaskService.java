package com.message.job.service;

import com.message.common.domin.MessageTaskInfo;
import org.springframework.stereotype.Service;

@Service
public interface MessageSendTaskService {

    void processMessageTasks();

    Boolean addTaskInfo();

    void sendEmail(String configId, MessageTaskInfo messageTaskInfo);
}
