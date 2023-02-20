package com.iqilu.message.transfer.service.inside.impl;

import com.aliyun.core.utils.StringUtils;
import com.iqilu.message.transfer.dao.AppSecretDao;
import com.iqilu.message.transfer.dao.SocketMessageDao;
import com.iqilu.message.transfer.exception.CustomException;
import com.iqilu.message.transfer.pojo.AppSecret;
import com.iqilu.message.transfer.pojo.MessageBody;
import com.iqilu.message.transfer.service.inside.InsideService;
import com.iqilu.message.transfer.service.inside.management.AsyncInsideMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author 卢斌
 */
@Service
public class InsideServiceImpl implements InsideService {


    @Autowired
    private SocketMessageDao socketMessageDao;

    @Autowired
    private AsyncInsideMessage asyncInsideMessage;

    @Value("${socket.inside-secret-key}")
    private String appSecretKey;

    @Autowired
    private AppSecretDao appSecretDao;

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;


    /**
     * 散装发送多条socket消息
     * <p>
     *
     * @param senderPrimaryKey 发送者唯一标识
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

    /**
     * 刷新缓存的APP-SECRETE
     */
    @Override
    public void refreshInsideAppSecret() {
        List<AppSecret> secrets = appSecretDao.listAllAppSecrets();
        Map<String, AppSecret> mapParam = new HashMap<>();
        if (! CollectionUtils.isEmpty(secrets)) {
            for (AppSecret appSecret : secrets) {
                mapParam.put(appSecret.getAppId(), appSecret);
            }
        }
        redisTemplate.opsForHash().putAll(appSecretKey, mapParam);
    }
}
