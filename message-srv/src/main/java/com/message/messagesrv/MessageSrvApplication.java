package com.message.messagesrv;

import com.message.common.config.MyMetaObjectHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.message.common.mapper")
public class MessageSrvApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageSrvApplication.class, args);
    }

    @Bean
    public MyMetaObjectHandler metaObjectHandler() {
        return new MyMetaObjectHandler();
    }
}
