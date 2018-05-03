package com.security.util;

import com.base.jwt.IJWTInfo;
import com.base.jwt.JWTHelper;
import com.security.configuration.KeyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author fanzhenxing
 * @create 2018/4/27 10:02 AM
 */
@Configuration
public class ClientTokenUtil {

    @Value("${client.expire}")
    private int expire;

    @Autowired
    private KeyConfiguration keyConfiguration;

    public String generateToken(IJWTInfo ijwtInfo) throws Exception {
        return JWTHelper.generateToken(ijwtInfo, keyConfiguration.getServicePriKey(), expire);
    }

    public IJWTInfo getInfoFromtoToken(String token) throws Exception {
        return JWTHelper.getInfoFromToken(token, keyConfiguration.getServicePubKey());
    }


}
