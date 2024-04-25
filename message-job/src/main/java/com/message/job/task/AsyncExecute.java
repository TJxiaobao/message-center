package com.message.job.task;

import com.message.common.domin.MessageRecord;
import lombok.extern.slf4j.Slf4j;

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
            send(messageRecord);
            log.info("执行版本为" + messageRecord.getMsgType() + "任务id" + messageRecord.getMessageTaskId());
        } catch (Exception e) {
            log.error("id为" + messageRecord.getMessageTaskId() + "的任务执行失败,抛出异常" + e);
            throw new RuntimeException(e);
        }
    }

    public void send(MessageRecord messageRecord) {
        // todo 实现发送业务
    }
}
