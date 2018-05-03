package com.security.service;

import com.base.bean.JwtAuthenticationRequest;

public interface AuthService {
    String login(JwtAuthenticationRequest authenticationRequest) throws Exception;

    String refresh(String oldToken);

    void validate(String token) throws Exception;

    Boolean invalid(String token);

}
