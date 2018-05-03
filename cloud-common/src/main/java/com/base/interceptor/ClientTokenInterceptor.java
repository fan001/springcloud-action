package com.base.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fanzhenxing
 * @create 2018/4/27 7:57 PM
 */
@Slf4j
public class ClientTokenInterceptor implements RequestInterceptor {

    private String clientTokenHeader;
    private String token;

    public ClientTokenInterceptor(String clientTokenHeader, String token) {
        this.clientTokenHeader = clientTokenHeader;
        this.token = token;
    }

    @Override
    public void apply(RequestTemplate template) {

        try {
            log.error("the template url in inteceptor is {} ..........." ,template.url());
            template.header(clientTokenHeader,token);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
