package com.iqilu.message.transfer.service.sms;

import com.alibaba.fastjson.JSONObject;
import com.iqilu.message.transfer.aop.PrimaryKeyParam;
import com.iqilu.message.transfer.aop.StrParam;
import com.iqilu.message.transfer.pojo.MessageBody;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 卢斌
 */
@Validated
public interface SmsService {


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
    void sendSmsMessagePrimary(@PrimaryKeyParam(max = Long.MAX_VALUE) Long senderPhoneNumber,
                               @StrParam String templateCode,
                               @StrParam String signName,
                               @NotNull JSONObject templateParam,
                               List<Long> phoneList);

    /**
     * 散装发送多条短信
     * <p>
     * 短信发送目标收到的短信内容允许不一样，但是必须使用同一个阿里短信模板。
     *
     * @param senderPhoneNumber 发送者电话号码
     * @param templateCode      模板编号
     * @param messageList       消息列表
     */
    void sendSmsMessageBulk(@PrimaryKeyParam(max = Long.MAX_VALUE) Long senderPhoneNumber, @StrParam String templateCode, String singName, List<MessageBody> messageList);


}
