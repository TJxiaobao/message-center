package com.iqilu.message.transfer.configuration;

import com.iqilu.message.transfer.service.inside.InsideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/**
 * @author 卢斌
 */
@Component
public class InitInsideMessageSecret implements ApplicationRunner {

    @Autowired
    private InsideService insideService;

    @Override
    public void run(ApplicationArguments args) {
        insideService.refreshInsideAppSecret();
    }

}
