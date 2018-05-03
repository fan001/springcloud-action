package com.client.interceptor;

import com.client.config.ServiceAuthConfig;
import com.client.configuration.ServiceAuthUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

/**
 * @author fanzhenxing
 * @create 2018/4/29 10:21 AM
 */
public class ClientTokenInterceptor implements RequestInterceptor {
    @Autowired
    @Lazy
    private ServiceAuthUtil serviceAuthUtil;
    @Autowired
    @Lazy
    private ServiceAuthConfig serviceAuthConfig;

    @Override
    public void apply(RequestTemplate template) {
        try {
            template.header(serviceAuthConfig.getTokenHeader(),
                    serviceAuthUtil.getClientToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
