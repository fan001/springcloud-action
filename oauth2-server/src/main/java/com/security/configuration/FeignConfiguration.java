package com.security.configuration;

import com.security.inteceptor.ClientTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fanzhenxing
 * @create 2018/4/28 1:48 PM
 */
@Configuration
public class FeignConfiguration {
    @Bean
    ClientTokenInterceptor clientTokenInterceptor(){
        return new ClientTokenInterceptor();
    }
}
