package com.iqilu.message.transfer.websocket;

import io.netty.util.internal.StringUtil;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 卢斌
 */
public class WebSocketManager {
    /**
     * 保存连接 session 的地方
     */
    private static final ConcurrentHashMap<String, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();


    public static void add(String key, WebSocketSession session) {
        // 添加 session
        SESSION_POOL.put(key, session);
    }


    public static WebSocketSession remove(String key) {
        // 删除 session
        if (!StringUtil.isNullOrEmpty(key)) {
            return SESSION_POOL.remove(key);
        } else {
            return null;
        }
    }

    public static void removeAndClose(String key) {
        WebSocketSession session = remove(key);
        if (session != null) {
            try {
                // 关闭连接
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static WebSocketSession get(String key) {
        // 获得 session
        return SESSION_POOL.get(key);
    }

}
