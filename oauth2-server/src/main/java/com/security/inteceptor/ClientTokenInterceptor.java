package com.security.inteceptor;

import com.security.configuration.ClientConfiguration;
import com.security.service.AuthClientServer;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fanzhenxing
 * @create 2018/4/28 11:14 AM
 *
 */
@Slf4j
public class ClientTokenInterceptor implements RequestInterceptor {
    @Autowired
    private ClientConfiguration clientConfiguration;

    @Autowired
    private AuthClientServer authClientServer;

    /**
     * 请求拦截器中，增加客户端的相关信息
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        try {
            log.error("the url of the template request is:{} ",template.url());
            template.header(clientConfiguration.getClientTokenHeader(),
                    authClientServer.apply(clientConfiguration.getClientId(),
                            clientConfiguration.getClientSecret()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
