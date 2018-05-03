package com.security.inteceptor;

import com.base.jwt.IJWTInfo;
import com.security.configuration.UserConfiguration;
import com.security.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fanzhenxing
 * @create 2018/4/28 12:15 PM
 */
@Slf4j
public class UserRequestAuthInterceptor extends HandlerInterceptorAdapter{

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserConfiguration userConfigurations;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String token = request.getHeader(userConfigurations.getUserTokenHeader());
        IJWTInfo ijwtInfo = jwtTokenUtil.getInfoFromToken(token);


        return super.preHandle(request, response, handler);
    }
}
