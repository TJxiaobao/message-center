package com.iqilu.message.transfer.service.inside.impl;

import com.aliyun.core.utils.StringUtils;
import com.iqilu.message.transfer.dao.SocketMessageDao;
import com.iqilu.message.transfer.exception.CustomException;
import com.iqilu.message.transfer.pojo.MessageBody;
import com.iqilu.message.transfer.service.inside.InsideService;
import com.iqilu.message.transfer.service.inside.management.AsyncInsideMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author 卢斌
 */
@Service
public class InsideServiceImpl implements InsideService {


    @Autowired
    private SocketMessageDao socketMessageDao;


    @Autowired
    private AsyncInsideMessage asyncInsideMessage;


    /**
     * 散装发送多条短信
     * <p>
     * 短信发送目标收到的短信内容允许不一样，但是必须使用同一个阿里短信模板。
     *
     * @param senderPrimaryKey 发送者电话号码
     * @param appId            应用Id
     * @param messageList      消息列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pushInsideMessage(String senderPrimaryKey, String appId, List<MessageBody> messageList) {
        if (CollectionUtils.isEmpty(messageList)) {
            return;
        }
        if (StringUtils.isBlank(senderPrimaryKey) || StringUtils.isBlank(appId)) {
            throw new CustomException("请自定发送者和appId");
        }
        for (MessageBody eachMessage : messageList) {
            if (StringUtils.isBlank(eachMessage.getReceiver())) {
                throw new CustomException("存在未指定接收者的消息体");
            }
            eachMessage.setAppId(appId);
            eachMessage.setSender(senderPrimaryKey);
        }

        // 将待发送消息先保存到数据库中
        socketMessageDao.saveMessage(messageList);

        // 将保存到数据库的消息实体取出消息Id
        List<Long> messageIdList = new LinkedList<>();
        for (MessageBody eachMessageBody : messageList) {
            messageIdList.add(eachMessageBody.getId());
        }

        // 异步线程池发送socket消息
        asyncInsideMessage.asyncPushSocketMessage(appId, messageIdList);
    }
}
