package com.message.common.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.message.common.domin.EmailConfig;
import com.message.common.mapper.EmailConfigMapper;
import com.message.common.service.EmailConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailConfigServiceImpl extends ServiceImpl<EmailConfigMapper, EmailConfig>
        implements EmailConfigService {
}
