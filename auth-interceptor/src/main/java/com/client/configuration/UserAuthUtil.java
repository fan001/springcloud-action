package com.client.configuration;

import com.base.exception.auth.UserTokenException;
import com.base.jwt.IJWTInfo;
import com.base.jwt.JWTHelper;
import com.client.config.UserAuthConfig;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author fanzhenxing
 * @create 2018/4/26 2:46 PM
 */
@Configuration
public class UserAuthUtil {
    @Autowired
    private UserAuthConfig userAuthConfig;

    public IJWTInfo getInfoFromToken(String token) throws Exception {
        try {
            return JWTHelper.getInfoFromToken(token, userAuthConfig.getPubKeyByte());
        } catch (ExpiredJwtException e) {
            throw new UserTokenException("User token expired!");
        } catch (SignatureException e) {
            throw new UserTokenException("User token signature error!");
        } catch (IllegalArgumentException e) {
            throw new UserTokenException("User token is null or empty!");
        }
    }
}
