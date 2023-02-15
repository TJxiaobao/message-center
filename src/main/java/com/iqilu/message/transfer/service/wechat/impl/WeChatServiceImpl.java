package com.iqilu.message.transfer.service.wechat.impl;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.iqilu.message.transfer.pojo.MessageBody;
import com.iqilu.message.transfer.pojo.wechat.WeChatLoginResult;
import com.iqilu.message.transfer.service.wechat.WeChatService;
import com.iqilu.message.transfer.service.wechat.management.WeChatUserManagement;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 卢斌
 */
@Log4j2
@Service
public class WeChatServiceImpl implements WeChatService {


    @Value("${chat.application.token}")
    private String weChatSignToken;

    @Autowired
    private WeChatUserManagement weChatUserManagement;

    /**
     * 微信公众平台验签
     *
     * @param signature 微信加密签名
     * @param timestamp 时间戳字符串
     * @param nonce     随机数
     * @return 是否验签成功
     */
    @Override
    public boolean acceptSignature(String signature, String timestamp, String nonce) {
        ArrayList<String> paramArrayList = new ArrayList<String>(3) {{
            add(weChatSignToken);
            add(timestamp);
            add(nonce);
        }};
        Collections.sort(paramArrayList);
        StringBuilder signParam = new StringBuilder();
        for (String eachValue : paramArrayList) {
            signParam.append(eachValue);
        }
        Digester sha1 = new Digester(DigestAlgorithm.SHA1);
        String signValue = byteToHex(sha1.digest(signParam.toString()));
        return signature.equalsIgnoreCase(signValue);
    }

    /**
     * 用户登录与注册；
     * - 存在用户信息则登录，数据库中如果没有该用户的openId则先完成基础的注册
     *
     * @param code 登录code
     * @return token
     */
    @Override
    public String userLogin(String code) {
        // 调用微信小程序官方登录接口获取OpenId和SessionKey
        WeChatLoginResult loginResult = weChatUserManagement.requestLogin(code);
        return loginResult.getOpenId();
    }

    /**
     * 散装发送多条微信通知
     * <p>
     * 模板参数内容允许不一样，但是必须使用同一个微信消息推送模板
     *
     * @param messageList 消息列表
     */
    @Override
    public void sendWechatMessageBulk(List<MessageBody> messageList) {
        if (CollectionUtils.isEmpty(messageList)) {
            return;
        }
        for(MessageBody messageBody : messageList) {
            weChatUserManagement.sendWechatMessage(messageBody);
        }
    }


    /**
     * 十六进制字节数组转为字符串
     * @param hash  16进制数组
     * @return  字符串
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

}
