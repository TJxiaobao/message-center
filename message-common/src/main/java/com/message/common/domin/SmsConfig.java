package com.message.common.domin;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sms_config")
public class SmsConfig {

    private Long id;

    private String configId;

    private String supplier;

    private String accessKeyId;

    private String accessKeySecret;

    private String signature;

    private String templateId;
}
