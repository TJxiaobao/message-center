package com.message.job.task.strategy;

import com.message.job.task.type.AbstractSend;
import org.springframework.boot.CommandLineRunner;

import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

public class SendStrategyFactory implements CommandLineRunner {

    /**
     * strategy container
     */
    private static final ConcurrentHashMap<String, AbstractSend> SEND_STRATEGY = new ConcurrentHashMap<>();

    public static AbstractSend invoke(String type) {
        return SEND_STRATEGY.get(type);
    }


    @Override
    public void run(String... args) throws Exception {
        ServiceLoader<AbstractSend> loader = ServiceLoader.load(AbstractSend.class, AbstractSend.class.getClassLoader());
        for (AbstractSend collect : loader) {
            SEND_STRATEGY.put(collect.supportType(), collect);
        }
    }
}
