package com.security.inteceptor;

import com.base.exception.auth.ClientForbiddenException;
import com.base.jwt.IJWTInfo;
import com.security.configuration.ClientConfiguration;
import com.security.service.AuthClientServer;
import com.security.service.impl.DbAuthClientService;
import com.security.util.ClientTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fanzhenxing
 * @create 2018/4/28 12:10 PM
 */
public class ServiceRequestAuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ClientTokenUtil clientTokenUtil;

    @Autowired
    private AuthClientServer authClientService;

    @Autowired
    private ClientConfiguration clientConfiguration;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String token = request.getHeader(clientConfiguration.getClientTokenHeader());
        IJWTInfo ijwtInfo = clientTokenUtil.getInfoFromtoToken(token);
        String uniqueName = ijwtInfo.getUniqueName();
        for (String client : authClientService.getAllowedClient(clientConfiguration.getClientId())) {
            if (client.equals(uniqueName)) {
                return super.preHandle(request, response, handler);
            }
        }

        throw new ClientForbiddenException("Client is Forbidden");
    }
}
