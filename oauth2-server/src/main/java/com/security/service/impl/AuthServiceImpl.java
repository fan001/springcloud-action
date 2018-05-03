package com.security.service.impl;

import com.base.bean.JwtAuthenticationRequest;
import com.base.jwt.JWTInfo;
import com.base.util.StringUtils;
import com.base.vo.UserInfo;
import com.security.feign.IUserService;
import com.security.service.AuthService;
import com.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private JwtTokenUtil jwtTokenUtil;
    private IUserService userService;

    @Autowired
    public AuthServiceImpl(JwtTokenUtil jwtTokenUtil, IUserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;


    }

    @Override
    public String login(JwtAuthenticationRequest authenticationRequest) throws Exception {
        UserInfo info = userService.validate(authenticationRequest);
        String token = "";
        if (!StringUtils.isEmpty(info.getId())) {
            token = jwtTokenUtil.generateToken(new JWTInfo(info.getUsername(), info.getId(), info.getName()));
        }
        return token;
    }

    @Override
    public String refresh(String oldToken) {

        return null;
    }

    @Override
    public void validate(String token) throws Exception {
        jwtTokenUtil.getInfoFromToken(token);


    }

    @Override
    public Boolean invalid(String token) {
        return null;
    }
}
