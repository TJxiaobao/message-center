package com.iqilu.message.transfer.websocket;

import com.iqilu.message.transfer.service.inside.management.AsyncInsideMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

/**
 * @author 卢斌
 */
@Slf4j
@Component
public class WebSocketCallbackHandler extends TextWebSocketHandler {


    @Autowired
    private AsyncInsideMessage asyncInsideMessage;

    @Value("${socket.receive-command}")
    private String socketReceiveCommand;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        String appId = attributes.get("appId").toString();
        String primaryKey = attributes.get("userPrimaryKey").toString();
        String sessionKey = AsyncInsideMessage.buildSocketSessionKey(primaryKey, appId);
        WebSocketManager.add(sessionKey, session);
        // 建立连接后，返回established，表示握手成功
        session.sendMessage(new TextMessage("established"));
    }


    /**
     * 当接收到发送到来的socket消息时，如果服务端收到消息接收指令则回复缓存的未接收消息，否则视为心跳消息。
     */
    @Override
    @SuppressWarnings("NullableProblems")
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String messageText = message.getPayload();
        if (socketReceiveCommand.equalsIgnoreCase(messageText)) {
            Map<String, Object> attributes = session.getAttributes();
            String appId = attributes.get("appId").toString();
            String primaryKey = attributes.get("userPrimaryKey").toString();
            asyncInsideMessage.receiveMessage(appId, primaryKey);
        } else {
            session.sendMessage(message);
        }

    }


    /**
     * socket连接断开后，从会话队列中删除该socket会话。
     */
    @Override
    @SuppressWarnings("NullableProblems")
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Map<String, Object> attributes = session.getAttributes();
        String appId = attributes.get("appId").toString();
        String primaryKey = attributes.get("userPrimaryKey").toString();
        String sessionKey = AsyncInsideMessage.buildSocketSessionKey(primaryKey, appId);
        WebSocketManager.removeAndClose(sessionKey);
    }

}
