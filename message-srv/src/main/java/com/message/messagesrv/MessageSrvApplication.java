package com.message.messagesrv;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.message.common.mapper")
public class MessageSrvApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageSrvApplication.class, args);
    }

}
