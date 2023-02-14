package com.iqilu.message.transfer.service.sms.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.core.utils.StringUtils;
import com.iqilu.message.transfer.exception.CustomException;
import com.iqilu.message.transfer.pojo.MessageBody;
import com.iqilu.message.transfer.service.sms.SmsService;
import com.iqilu.message.transfer.service.sms.management.SmsExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author 卢斌
 */
@Service
public class SmsServiceImpl implements SmsService {


    @Value("${sms.batch-size}")
    private int smsBatchMaxSize;

    /**
     * 发送相同的短信内容到多个用户
     * <p>
     * 所有手机号参数中不得包含空格，必须为11位合法手机号
     *
     * @param senderPhoneNumber 发送人电话号码
     * @param templateCode      阿里云模板编号
     * @param signName          阿里验证通过的模板名称
     * @param templateParam     模板对应的模板消息参数
     * @param phoneList         接收人手机号列表
     */
    @Override
    public void sendSmsMessagePrimary(Long senderPhoneNumber, String templateCode, String signName, JSONObject templateParam, List<Long> phoneList) {
        if (CollectionUtils.isEmpty(phoneList)) {
            throw new CustomException("请指定消息发送目标");
        }
        Stack<Long> phoneArrayStack = new Stack<>();
        phoneArrayStack.addAll(new HashSet<>(phoneList));
        while (! CollectionUtils.isEmpty(phoneArrayStack)) {
            LinkedList<Long> batchPhone = new LinkedList<>();
            for (int i = 0; i < smsBatchMaxSize; i++) {
                if (phoneArrayStack.isEmpty()) {
                    break;
                }
                batchPhone.add(phoneArrayStack.pop());
            }
            SmsExecutor.sendBatch(batchPhone, signName, templateCode, templateParam);
        }
    }

    /**
     * 散装发送多条短信
     * <p>
     * 短信发送目标收到的短信内容允许不一样，但是必须使用同一个阿里短信模板。
     *
     * @param senderPhoneNumber 发送者电话号码
     * @param templateCode      模板编号
     * @param messageList       消息列表
     */
    @Override
    public void sendSmsMessageBulk(Long senderPhoneNumber, String templateCode, String signName, List<MessageBody> messageList) {
        if (CollectionUtils.isEmpty(messageList)) {
            return;
        }
        if (! StringUtils.isBlank(templateCode)) {
            if (StringUtils.isBlank(signName)) {
                throw new CustomException("请指定signName");
            }
        }
        LinkedList<Long> targetPhoneList = new LinkedList<>();
        LinkedList<String> signList = new LinkedList<>();
        JSONArray templateParam = new JSONArray();
        for (MessageBody eachMessage : messageList) {
            targetPhoneList.add(Long.parseLong(eachMessage.getReceiver()));
            signList.add((signName));
            templateParam.add(eachMessage.getTemplateParam());
        }
        SmsExecutor.sendBatchBulk(targetPhoneList, signList, templateCode, templateParam);
    }


}
