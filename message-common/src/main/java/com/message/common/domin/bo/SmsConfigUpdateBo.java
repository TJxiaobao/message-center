package com.message.common.domin.bo;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsConfigUpdateBo {

    /**
     * 配置 ID
     */
    @NotNull
    private Long id;

    /**
     * 配置 ID
     */
    @NotNull
    private String configId;

    /**
     * 运营商
     */
    @NotNull
    private String supplier;

    /**
     * accessKeyId
     */
    @NotNull
    private String accessKeyId;

    /**
     * accessKeySecret
     */
    @NotNull
    private String accessKeySecret;

    /**
     * 签名
     */
    @NotNull
    private String signature;

    /**
     * 模版 ID
     */
    @NotNull
    private String templateId;
}
