package com.iqilu.message.transfer.service.impl;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.iqilu.message.transfer.service.WeChatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;

/**
 * @author 卢斌
 */
@Service("WeChatService")
public class WeChatServiceImpl implements WeChatService {


    @Value("${chat.application.token}")
    private String weChatSignToken;

    /**
     * 微信公众平台验签
     *
     * @param signature 微信加密签名
     * @param timestamp 时间戳字符串
     * @param nonce     随机数
     * @return 是否验签成功
     */
    @Override
    public Boolean acceptSignature(String signature, String timestamp, String nonce) {
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
