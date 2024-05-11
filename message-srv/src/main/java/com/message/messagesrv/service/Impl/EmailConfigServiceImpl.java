package com.message.messagesrv.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.message.common.domin.EmailConfig;
import com.message.common.mapper.EmailConfigMapper;
import com.message.messagesrv.service.EmailConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailConfigServiceImpl extends ServiceImpl<EmailConfigMapper, EmailConfig>
        implements EmailConfigService {
}
