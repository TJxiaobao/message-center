package com.message.job.service;

import org.springframework.stereotype.Service;

@Service
public interface MessageSendTaskService {

    void processMessageTasks();

    Boolean addTaskInfo();
}
