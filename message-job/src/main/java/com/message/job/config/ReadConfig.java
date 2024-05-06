package com.message.job.config;

import cn.hutool.core.bean.BeanUtil;
import com.message.common.domin.SmsConfig;
import com.message.job.utils.ConfigMapUtils;
import org.dromara.sms4j.core.datainterface.SmsReadConfig;
import org.dromara.sms4j.provider.config.BaseConfig;
import org.dromara.sms4j.unisms.config.UniConfig;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ReadConfig implements SmsReadConfig {
    @Override
    public BaseConfig getSupplierConfig(String configId) {
        //这里需要注意，UniConfig只是代表合一短信的配置类，仅做示例使用，根据不同厂商选择实例化不同的配置类
        UniConfig uniConfig = new UniConfig();
        //此处仅为示例，实际环境中，数据可以来自任意位置，
        return uniConfig;
    }

    @Override
    public List<BaseConfig> getSupplierConfigList() {
        Map<String, SmsConfig> configMap = ConfigMapUtils.getConfigMap();
        List<BaseConfig> baseConfigs = new ArrayList<>();
        for (Map.Entry<String, SmsConfig> smsConfigEntry : configMap.entrySet()) {
            SmsConfig config = smsConfigEntry.getValue();
            BaseConfig baseConfig = new BaseConfig() {
                @Override
                public String getSupplier() {
                    return config.getSupplier();
                }
            };
            BeanUtil.copyProperties(config, baseConfig);
            baseConfigs.add(baseConfig);
        }
        return baseConfigs;
    }
}