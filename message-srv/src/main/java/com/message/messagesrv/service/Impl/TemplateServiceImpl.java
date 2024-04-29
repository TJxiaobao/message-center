package com.message.messagesrv.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.message.common.domin.MessageTemplate;
import com.message.common.domin.bo.TemplateBo;
import com.message.common.mapper.MessageTemplateMapper;
import com.message.messagesrv.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final MessageTemplateMapper messageTemplateMapper;

    @Override
    public Boolean addTemplate(TemplateBo templateBo) {
        MessageTemplate messageTemplate = BeanUtil.toBean(templateBo, MessageTemplate.class);
        return messageTemplateMapper.insert(messageTemplate) > 0;
    }
}
