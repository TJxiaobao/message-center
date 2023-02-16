package com.iqilu.message.transfer.websocket;

import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;

/**
 * @author 卢斌
 */
@SuppressWarnings("NullableProblems")
@Component
public class WebSocketInterceptor implements HandshakeInterceptor {


    private final RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    public WebSocketInterceptor(RedisTemplate<String, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        // 尝试获取用户传到的token
        String appId = servletRequest.getParameter("appId");
        String userPrimaryKey = servletRequest.getParameter("userPrimaryKey");
        if (StringUtils.isBlank(appId) || StringUtils.isBlank(userPrimaryKey)) {
            return false;
        } else {
            attributes.put("appId", appId);
            attributes.put("userPrimaryKey", userPrimaryKey);
            return true;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
