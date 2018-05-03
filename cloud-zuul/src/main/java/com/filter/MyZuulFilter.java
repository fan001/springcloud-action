package com.filter;

import com.alibaba.fastjson.JSON;
import com.base.bean.ResultBean;
import com.base.context.BaseContextHandler;
import com.base.jwt.IJWTInfo;
import com.base.vo.PermissionInfo;
import com.client.config.ServiceAuthConfig;
import com.client.config.UserAuthConfig;
import com.client.configuration.UserAuthUtil;
import com.feign.IPermissionService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class MyZuulFilter extends ZuulFilter {

    @Autowired
    @Lazy
    private IPermissionService permissionService;

    @Autowired
    UserAuthConfig userAuthConfig;

    @Autowired
    UserAuthUtil userAuthUtil;

    @Autowired
    ServiceAuthConfig serviceAuthConfig;


    @Value("${gate.ignore.startWith}")
    private String startWith;

    @Value("${zuul.prefix}")
    private String zuulPrefix;


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {


        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        final String requestUri = request.getRequestURI().substring(zuulPrefix.length());
        final String method = request.getMethod();

        BaseContextHandler.setToken(null);

        if (isStartWith(requestUri)) {
            return null;
        }

        IJWTInfo user = null;
        try {
            user = getJWTUser(request, ctx);
        } catch (Exception e) {
            ResultBean<String> resultBean = new ResultBean<>(e.getMessage());
            resultBean.setCode(ResultBean.TOKEN_ERROR_CODE);
            setFailedRequest(JSON.toJSONString(resultBean),200);
            return null;
        }

        List<PermissionInfo> permissionInfos = permissionService.getAllPermissionInfo();

        Stream<PermissionInfo> stream = getPermissionIfs(requestUri,method,permissionInfos);

        List<PermissionInfo> result = stream.collect(Collectors.toList());
        PermissionInfo[] permissionInfos1 = result.toArray(new PermissionInfo[]{});

        if(permissionInfos1.length >0){
            checkUserPermission(permissionInfos1,ctx,user);

        }
       /* ctx.addZuulRequestHeader(serviceAuthConfig.getTokenHeader()
                ,);*/


        ctx.setResponseBody("this is my message");
        ctx.setSendZuulResponse(false);
        return null;
    }


    /**
     * URI是否以什么打头
     *
     * @param requestUri
     * @return
     */
    private boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : startWith.split(",")) {
            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return flag;
    }


    /**
     * 返回session中的用户信息
     *
     * @param request
     * @param ctx
     * @return
     */
    private IJWTInfo getJWTUser(HttpServletRequest request, RequestContext ctx) throws Exception {
        String authToken = request.getHeader(userAuthConfig.getTokenHeader());
        if (StringUtils.isBlank(authToken)) {
            authToken = request.getParameter("token");
        }
        ctx.addZuulRequestHeader(userAuthConfig.getTokenHeader(), authToken);
        BaseContextHandler.setToken(authToken);
        return userAuthUtil.getInfoFromToken(authToken);
    }

    /**
     * 网关异常处理
     * @param body
     * @param code
     */
    private void setFailedRequest(String body, int code) {
        log.error("Reporting error ({}):{}", code, body);
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(code);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(body);
            ctx.setSendZuulResponse(false);
        }
    }

    /**
     * 获取能够匹配上当前url的请求
     * @param requestUri
     * @param method
     * @param serviceInfo
     * @return
     */
    private Stream<PermissionInfo> getPermissionIfs(final String requestUri,final String method,List<PermissionInfo> serviceInfo){
        return serviceInfo.parallelStream().filter(new Predicate<PermissionInfo>() {
            @Override
            public boolean test(PermissionInfo permissionInfo) {
                String url = permissionInfo.getUrl();
                String uri = url.replaceAll("\\{\\*\\}", "[a-zA-Z\\\\d]+");
                String regEx = "^" + uri + "$";
                return (Pattern.compile(regEx).matcher(requestUri).find() || requestUri.startsWith(url + "/"));
            }
        });
    }

    private void checkUserPermission(PermissionInfo[] permissionInfos,RequestContext ctx,IJWTInfo user){
        List<PermissionInfo> myPermissionList = permissionService.getPermissionByUsername(user.getUniqueName());
        PermissionInfo current = null;
        for(PermissionInfo info:permissionInfos){
            boolean anyMatch = myPermissionList.parallelStream().anyMatch(new Predicate<PermissionInfo>() {
                @Override
                public boolean test(PermissionInfo permissionInfo) {
                    return permissionInfo.getCode().equals(info.getCode());
                }
            });
            if(anyMatch){
                current = info;
                break;
            }
        }

        if(current == null){
            ResultBean<String> resultBean = new ResultBean<>("Token Forbidden");
            resultBean.setCode(ResultBean.TOKEN_FORBIDDEN_CODE);
            setFailedRequest(JSON.toJSONString(resultBean),200);
        }else {
        }
    }
}
