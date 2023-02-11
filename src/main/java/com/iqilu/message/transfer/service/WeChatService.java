package com.iqilu.message.transfer.service;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @author 卢斌
 */
@Validated
public interface WeChatService {

    /**
     * 微信公众平台验签
     *
     * @param signature 微信加密签名
     * @param timestamp 时间戳字符串
     * @param nonce     随机数
     * @return  是否验签成功
     */
    boolean acceptSignature(@NotBlank String signature,@NotBlank String timestamp,@NotBlank String nonce);
}
