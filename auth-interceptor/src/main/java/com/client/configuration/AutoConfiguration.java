package com.client.configuration;

import com.client.config.ServiceAuthConfig;
import com.client.config.UserAuthConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fanzhenxing
 * @create 2018/4/26 3:22 PM
 */
@Configuration
public class AutoConfiguration {

    @Bean
    ServiceAuthConfig getServiceAuthConfig(){
        return new ServiceAuthConfig();
    }

    @Bean
    UserAuthConfig getUserAuthConfig(){
        return new UserAuthConfig();
    }
}
