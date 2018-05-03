package com.security.util;

import com.base.jwt.IJWTInfo;
import com.base.jwt.JWTHelper;
import com.security.configuration.KeyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {
    @Value("${jwt.expire}")
    private int expire;
    @Autowired
    private KeyConfiguration keyConfiguration;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public String generateToken(IJWTInfo ijwtInfo) throws Exception{
        return JWTHelper.generateToken(ijwtInfo,keyConfiguration.getUserPriKey(),expire);
    }

    public IJWTInfo getInfoFromToken(String token) throws Exception{
        return JWTHelper.getInfoFromToken(token,keyConfiguration.getUserPubKey());
    }




}
