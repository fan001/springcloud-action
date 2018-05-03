package com.security.configuration;

import com.security.inteceptor.ServiceRequestAuthInterceptor;
import com.security.inteceptor.UserRequestAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author fanzhenxing
 * @create 2018/4/28 1:44 PM
 */
@Configuration
@Primary
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * 将拦截器生效
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(serviceRequestAuthInterceptor()).addPathPatterns("/client/**");
        registry.addInterceptor(userRequestAuthInterceptor()).addPathPatterns("/service/**");
    }

    @Bean
    public ServiceRequestAuthInterceptor serviceRequestAuthInterceptor(){
        return new ServiceRequestAuthInterceptor();
    }

    @Bean
    UserRequestAuthInterceptor userRequestAuthInterceptor(){
        return new UserRequestAuthInterceptor();
    }

}
