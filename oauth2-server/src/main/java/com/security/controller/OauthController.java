package com.security.controller;

import com.base.bean.JwtAuthenticationRequest;
import com.base.bean.ResultBean;
import com.security.service.AuthService;
import com.security.util.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt")
public class OauthController {

    @Value("${jwt.token-header}")
    private String tokenHeader;
    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/token",method = RequestMethod.POST)
    public ResultBean<?> createToken(JwtAuthenticationRequest authenticationRequest) throws Exception {
        System.out.println(authenticationRequest.getUsername() + "aaaa is loginning ....");
        final String token = authService.login(authenticationRequest);
        return new ResultBean<>(new JwtAuthenticationResponse(token));
    }
}
