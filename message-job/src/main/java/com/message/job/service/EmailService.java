package com.message.job.service;

import com.message.common.domin.bo.EmailInfoBo;


public interface EmailService {

    public void sendEmail(String configId, EmailInfoBo emailInfoBo);

}
