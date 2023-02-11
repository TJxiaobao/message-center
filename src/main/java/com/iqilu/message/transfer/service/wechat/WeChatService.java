package com.iqilu.message.transfer.service.wechat;

import org.hibernate.validator.constraints.Length;
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


    /**
     * 用户登录与注册；
     * - 存在用户信息则登录，数据库中如果没有该用户的openId则先完成基础的注册
     *
     * @param code  登录code
     * @return  token
     */
    String userLogin(@NotBlank@Length(max = 128) String code);


}
