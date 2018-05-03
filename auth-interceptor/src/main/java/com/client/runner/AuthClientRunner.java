package com.client.runner;

import com.base.bean.ResultBean;
import com.client.config.ServiceAuthConfig;
import com.client.config.UserAuthConfig;
import com.client.feign.ServiceAuthFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author fanzhenxing
 * @create 2018/4/26 3:22 PM
 */
@Configuration
@Slf4j
public class AuthClientRunner implements CommandLineRunner {
    @Autowired
    private UserAuthConfig userAuthConfig;

    @Autowired
    private ServiceAuthConfig serviceAuthConfig;

    @Autowired
    private ServiceAuthFeign serviceAuthFeign;

    @Override
    public void run(String... args) throws Exception {
        log.error("初始化加载用户的pubkey ..........");
        try {
            refreshUserPubKey();
        } catch (Exception e) {
            log.error("初始化加载用户pubKey失败,1分钟后自动重试!",e);
        }


    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void refreshUserPubKey() {

        ResultBean<byte[]> resultBean = serviceAuthFeign.getUserPublicKey(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        if (resultBean.getCode() == ResultBean.SUCCESS) {
            this.userAuthConfig.setPubKeyByte(resultBean.getData());

        }

    }
}
