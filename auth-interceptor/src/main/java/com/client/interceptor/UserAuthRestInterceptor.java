package com.client.interceptor;

import com.base.context.BaseContextHandler;
import com.base.jwt.IJWTInfo;
import com.base.util.StringUtils;
import com.client.config.UserAuthConfig;
import com.client.configuration.UserAuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fanzhenxing
 * @create 2018/4/27 8:18 PM
 */

/**
 * 通过拦截器对到来的请求进行检查，查看user token是否满足合法,如果需要检查，则注入该Inteceptor即可
 */
@Slf4j
public class UserAuthRestInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserAuthUtil userAuthUtil;
    @Autowired
    private UserAuthConfig userAuthConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        String token = request.getHeader(userAuthConfig.getTokenHeader());
        if (StringUtils.isEmpty(token)) {
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals(userAuthConfig.getTokenHeader())) {
                        token = cookie.getValue();
                    }
                }
            }
        }

        IJWTInfo jwtUserInfo = userAuthUtil.getInfoFromToken(token);
        BaseContextHandler.setUsername(jwtUserInfo.getUniqueName());
        BaseContextHandler.setName(jwtUserInfo.getName());
        BaseContextHandler.setUserID(jwtUserInfo.getId());
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContextHandler.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}
