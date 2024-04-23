package com.iqilu.message.transfer.service.inside.management;

import com.iqilu.message.transfer.dao.SocketMessageDao;
import com.iqilu.message.transfer.pojo.MessageBody;
import com.iqilu.message.transfer.websocket.WebSocketManager;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 卢斌
 */
@Log4j2
@Component
public class AsyncInsideMessage {

    @Autowired
    private SocketMessageDao socketMessageDao;

    /**
     * 异步发送站内消息
     */
    @Async("insideMessageThreadLoop")
    public void asyncPushSocketMessage(String appId, List<Long> messageBodyIdList) {
        if (CollectionUtils.isEmpty(messageBodyIdList) || StringUtils.isBlank(appId)) {
            return;
        }

        // 筛检出Id列表中所属在APPId下的所有消息内容
        List<MessageBody> messageBodyList = socketMessageDao.listMessageBodyInIdList(appId, messageBodyIdList);
        if (CollectionUtils.isEmpty(messageBodyList)) {
            return;
        }

        // sendSuccessList，保存发送成功的消息（messageBody）的Id
        List<Long> sendSuccessList = new LinkedList<>();


        // 缓存的多条未发送消息循环发送（因为是后台消息是逐条发的为了保证消息真实性所以发送缓存内容也应该逐条发送）
        for (MessageBody messageBody : messageBodyList) {
            String receiver = messageBody.getReceiver();
            String receiverSessionKey = buildSocketSessionKey(receiver, appId);
            WebSocketSession socketSession = WebSocketManager.get(receiverSessionKey);
            if (socketSession == null) {
                continue;
            }
            try {
                // 如果消息接收者此时存在于服务端的socket会话，则发送socket消息并标记发送成功
                socketSession.sendMessage(new TextMessage(messageBody.getMessageContent()));
            } catch (IOException e) {
                log.warn("socket发送失败：{}", receiverSessionKey);
                e.printStackTrace();
                continue;
            }

            // 标记发送成功的消息Id
            sendSuccessList.add(messageBody.getId());
        }
        if (!CollectionUtils.isEmpty(sendSuccessList)) {
            socketMessageDao.deleteBufferedMessage(appId, sendSuccessList);
        }
    }


    /**
     * 主动接收消息
     *
     * @param appId              APPID
     * @param receiverPrimaryKey 用户唯一识别
     */
    public void receiveMessage(String appId, String receiverPrimaryKey) {
        String receiverSessionKey = buildSocketSessionKey(receiverPrimaryKey, appId);
        WebSocketSession webSocketSession = WebSocketManager.get(receiverSessionKey);
        if (webSocketSession == null) {
            return;
        }
        List<MessageBody> messageBodyList = socketMessageDao.listAllNeedReceiveMessage(receiverPrimaryKey, appId);
        if (!CollectionUtils.isEmpty(messageBodyList)) {
            // sendSuccessList，保存发送成功的消息（messageBody）的Id
            List<Long> sendSuccessList = new LinkedList<>();
            for (MessageBody messageBody : messageBodyList) {
                try {
                    webSocketSession.sendMessage(new TextMessage(messageBody.getMessageContent()));
                } catch (IOException e) {
                    log.warn("socket发送失败：{}", receiverSessionKey);
                    e.printStackTrace();
                    continue;
                }
                sendSuccessList.add(messageBody.getId());
            }
            if (!CollectionUtils.isEmpty(sendSuccessList)) {
                socketMessageDao.deleteBufferedMessage(appId, sendSuccessList);
            }
        }
    }


    /**
     * 通过appId和用户唯一标识生成一个socket会话唯一标识
     *
     * @param userPrimaryKey 用户唯一标识
     * @param appId          appId
     * @return socket会话池的Key
     */
    public static String buildSocketSessionKey(String userPrimaryKey, String appId) {
        return "" + appId + "-" + userPrimaryKey;
    }


}
