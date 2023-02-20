package com.iqilu.message.transfer.websocket;

import cn.hutool.crypto.digest.MD5;
import com.iqilu.message.transfer.pojo.AppSecret;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Value("${socket.inside-secret-key}")
    private String appSecretKey;


    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        // 尝试获取用户传到的token
        String appId = servletRequest.getParameter("appId");
        String userPrimaryKey = servletRequest.getParameter("userPrimaryKey");
        String timestamp = servletRequest.getParameter("timestamp");
        String sign = servletRequest.getParameter("sign");
        if (StringUtils.isBlank(timestamp) || StringUtils.isBlank(sign) || StringUtils.isBlank(appId)) {
            return false;
        }
        Object secretObject = redisTemplate.opsForHash().get(appSecretKey, appId);
        if (secretObject == null) {
            return false;
        } else {
            AppSecret appSecret = (AppSecret) secretObject;
            return appSecret.checkSign(sign, timestamp);
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }




}
