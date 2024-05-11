package com.message.common.domin.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsConfigBo {

    /**
     * 配置 ID
     */
    private Long id;

    /**
     * 配置 ID
     */
    private String configId;

    /**
     * 运营商
     */
    private String supplier;

    /**
     * accessKeyId
     */
    private String accessKeyId;

    /**
     * accessKeySecret
     */
    private String accessKeySecret;

    /**
     * 签名
     */
    private String signature;

    /**
     * 模版 ID
     */
    private String templateId;
}
