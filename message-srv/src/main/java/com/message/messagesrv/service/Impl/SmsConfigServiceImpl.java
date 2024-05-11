package com.message.messagesrv.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.message.common.domin.SmsConfig;
import com.message.common.mapper.SmsConfigMapper;
import com.message.messagesrv.service.SmsConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SmsConfigServiceImpl extends ServiceImpl<SmsConfigMapper, SmsConfig>
        implements SmsConfigService {
}
