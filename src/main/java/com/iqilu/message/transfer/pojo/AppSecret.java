package com.iqilu.message.transfer.pojo;

import cn.hutool.crypto.digest.MD5;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author 卢斌
 */
@Getter
@Setter
@NoArgsConstructor
public class AppSecret implements Serializable {

    private Long id;

    private String appId;

    private String secret;


    /**
     * 验签
     *
     * @param signature 签名参数
     * @param timestamp 时间戳
     * @return  验签结果
     */
    public boolean checkSign(String signature, String timestamp) {
        if (StringUtils.isBlank(timestamp)) {
            return false;
        }
        MD5 md5 = new MD5();
        String sign = md5.digestHex(this.appId + timestamp + this.secret);
        return sign.equalsIgnoreCase(signature);
    }

}
