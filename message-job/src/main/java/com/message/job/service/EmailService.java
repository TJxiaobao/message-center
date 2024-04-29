package com.message.job.service;

import com.message.common.domin.MessageTaskInfo;
import com.message.common.domin.bo.EmailInfoBo;


public interface EmailService {

    public void sendEmail(String configId, MessageTaskInfo messageTaskInfo);

}
