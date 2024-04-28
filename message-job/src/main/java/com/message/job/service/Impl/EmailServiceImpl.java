package com.message.job.service.Impl;

import com.message.common.domin.bo.EmailInfoBo;
import com.message.job.config.MailSenderConfig;
import com.message.job.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Objects;


@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    MailSenderConfig senderConfig;

    @Override
    public void sendEmail(String configId, EmailInfoBo emailInfoBo) {
        JavaMailSenderImpl mailSender = senderConfig.getSender(configId);
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(Objects.requireNonNull(mailSender.getUsername()));
            helper.setTo(emailInfoBo.getTo());
//            helper.setFrom("");
            helper.setSubject(emailInfoBo.getSubject());
            helper.setText(emailInfoBo.getContent(), true);
            mailSender.send(mimeMessage);
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
    }
}
