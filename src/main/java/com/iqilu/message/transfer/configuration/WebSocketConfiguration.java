package com.iqilu.message.transfer.configuration;


import com.iqilu.message.transfer.websocket.WebSocketCallbackHandler;
import com.iqilu.message.transfer.websocket.WebSocketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author 卢斌
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final WebSocketCallbackHandler webSocketCallbackHandler;

    private final WebSocketInterceptor webSocketInterceptor;

    @Autowired
    public WebSocketConfiguration(WebSocketCallbackHandler webSocketCallbackHandler, WebSocketInterceptor webSocketInterceptor) {
        this.webSocketCallbackHandler = webSocketCallbackHandler;
        this.webSocketInterceptor = webSocketInterceptor;
    }


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketCallbackHandler, "/message")
                .addInterceptors(webSocketInterceptor)
                .setAllowedOrigins("*");
    }
}
