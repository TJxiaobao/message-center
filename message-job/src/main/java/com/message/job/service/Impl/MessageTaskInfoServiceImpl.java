package com.message.job.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.message.common.domin.MessageTaskInfo;
import com.message.common.mapper.MessageTaskInfoMapper;
import com.message.job.service.MessageTaskInfoService;
import org.springframework.stereotype.Service;


@Service
public class MessageTaskInfoServiceImpl extends ServiceImpl<MessageTaskInfoMapper, MessageTaskInfo> implements MessageTaskInfoService {
}
