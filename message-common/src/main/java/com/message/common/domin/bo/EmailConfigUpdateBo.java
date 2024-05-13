package com.message.common.domin.bo;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailConfigUpdateBo {


    @NotNull
    private Long id;


    /**
     * 配置 ID
     */
    @NotNull
    private String configId;

    /**
     * 用户名
     */
    @NotNull
    private String username;

    /**
     * 授权码
     */
    @NotNull
    private String password;

    /**
     * host
     */
    @NotNull
    private String host;

    /**
     * 端口
     */
    @NotNull
    private Integer port;

    /**
     * 协议
     */
    @NotNull
    private String protocol;

    /**
     * 默认编码
     */
    @NotNull
    private String defaultEncoding;
}