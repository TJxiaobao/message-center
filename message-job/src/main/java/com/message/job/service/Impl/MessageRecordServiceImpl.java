package com.message.job.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.message.common.domin.MessageRecord;
import com.message.common.mapper.MessageRecordMapper;
import com.message.job.service.MessageRecordService;
import org.springframework.stereotype.Service;

@Service
public class MessageRecordServiceImpl extends ServiceImpl<MessageRecordMapper, MessageRecord>
        implements MessageRecordService {
}
