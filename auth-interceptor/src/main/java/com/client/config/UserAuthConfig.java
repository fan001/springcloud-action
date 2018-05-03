package com.client.config;

import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fanzhenxing
 * @create 2018/4/26 2:44 PM
 */
public class UserAuthConfig {

    @Value("${auth.user.token-header}")
    private String tokenHeader;

    private byte[] pubKeyByte;

    public String getToken(HttpServletRequest request){
        return request.getHeader(this.getTokenHeader());
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public byte[] getPubKeyByte() {
        return pubKeyByte;
    }

    public void setPubKeyByte(byte[] pubKeyByte) {
        this.pubKeyByte = pubKeyByte;
    }
}
