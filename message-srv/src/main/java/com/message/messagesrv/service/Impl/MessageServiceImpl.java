package com.message.messagesrv.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.message.common.domin.MessageTaskInfo;
import com.message.common.domin.bo.MessageTaskInfoBo;
import com.message.common.enums.MessageTypeEnum;
import com.message.common.mapper.MessageTaskInfoMapper;
import com.message.messagesrv.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl extends ServiceImpl<MessageTaskInfoMapper, MessageTaskInfo>
        implements MessageService {

    private final MessageTaskInfoMapper messageTaskInfoMapper;

    @Override
    public Boolean addMsg(MessageTaskInfoBo messageTaskInfoBo) {
        ArrayList<MessageTaskInfo> messageTaskInfos = new ArrayList<>();
        for (String receiver : messageTaskInfoBo.getReceiver()) {
            for (String msgType : messageTaskInfoBo.getMessageType()) {
                MessageTaskInfo messageTaskInfo = new MessageTaskInfo();
                messageTaskInfo.setReceiver(receiver);
                messageTaskInfo.setMsgTaskType(getMsgTypeCode(msgType));
                BeanUtil.copyProperties(messageTaskInfoBo, messageTaskInfo);
                messageTaskInfos.add(messageTaskInfo);
            }
        }

        // 任务进行保存 todo
        // messageTaskInfoMapper.i
        return true;
    }

    /**
     * 获取类型 code
     *
     * @param msgTypeString msgType String
     * @return code
     */
    private int getMsgTypeCode(String msgTypeString) {
        if (MessageTypeEnum.EMAIL.getStatusName().equals(msgTypeString)) {
            return MessageTypeEnum.EMAIL.getStatusCode();
        } else if (MessageTypeEnum.SMS.getStatusName().equals(msgTypeString)) {
            return MessageTypeEnum.SMS.getStatusCode();
        } else {
            return -1;
        }
    }
}
