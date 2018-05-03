package com.security.configuration;

import com.base.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fanzhenxing
 * @create 2018/4/29 10:09 AM
 */
@Configuration
public class AutoConfiguration {

    @Bean
    public GlobalExceptionHandler globalExceptionHandler(){
        return new GlobalExceptionHandler();
    }
}
