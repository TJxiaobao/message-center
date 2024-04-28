package com.message.common.domin;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "email_config")
public class EmailConfig {


    private Long id;

    /**
     * 配置 ID
     */
    private String configId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 授权码
     */
    private String password;
    /**
     * host
     */
    private String host;
    /**
     * 端口
     */
    private Integer port;
    /*** 协议 */
    private String protocol;
    /**
     * 默认编码
     */
    private String defaultEncoding;
}