package com.client.interceptor;

import com.base.exception.auth.ClientForbiddenException;
import com.base.jwt.IJWTInfo;
import com.client.config.ServiceAuthConfig;
import com.client.configuration.ServiceAuthUtil;
import com.client.configuration.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 对请求进行检查，查看client 是否为合法请求，需要检查，则注入该interceptor即可
 * @author fanzhenxing
 * @create 2018/4/27 7:15 PM
 */
public class ServiceAuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ServiceAuthUtil serviceAuthUtil;

    @Autowired
    private ServiceAuthConfig serviceAuthConfig;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String token = request.getHeader(serviceAuthConfig.getTokenHeader());
        IJWTInfo clientInfo = serviceAuthUtil.getInfoFromToken(token);
        String uniqueName = clientInfo.getUniqueName();
        for(String client:serviceAuthUtil.getAllowedClient()){
            if(client.equals(uniqueName)){
                return super.preHandle(request,response,handler);
            }
        }
     throw new ClientForbiddenException("Client is Forbidden");
    }
}
