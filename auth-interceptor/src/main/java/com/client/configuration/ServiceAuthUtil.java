package com.client.configuration;

import com.base.bean.ResultBean;
import com.base.exception.auth.ClientTokenException;
import com.base.jwt.IJWTInfo;
import com.base.jwt.JWTHelper;
import com.client.config.ServiceAuthConfig;
import com.client.feign.ServiceAuthFeign;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * @author fanzhenxing
 * @create 2018/4/26 9:30 PM
 */
@Configuration
@Slf4j
@EnableScheduling
public class ServiceAuthUtil {

    @Autowired
    private ServiceAuthConfig serviceAuthConfig;

    @Autowired
    private ServiceAuthFeign serviceAuthFeign;

    private List<String> allowedClient;
    private String clientToken;

    public IJWTInfo getInfoFromToken(String token) throws Exception {
        try {
            return JWTHelper.getInfoFromToken(token, serviceAuthConfig.getPubKeyByte());
        } catch (ExpiredJwtException e) {
            throw new ClientTokenException("client token expired");
        } catch (SignatureException e) {
            throw new ClientTokenException("client token signature error");
        } catch (IllegalArgumentException e) {
            throw new ClientTokenException("client token is null or empty");
        }
    }


    @Scheduled(cron = "0/30 * * * * ?")
    public void refreshAllowedClient() {
        log.error("refresh allowedClient ..........");
        ResultBean<List<String>> resultBean = serviceAuthFeign.getAllowedClient(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        this.allowedClient = resultBean.getData();
    }


    @Scheduled(cron = "0 0/10 * * * ?")
    public void refreshClientToken() {
        log.error("refresh client token ......");
        ResultBean<String> resultBean = serviceAuthFeign
                .getAccessToken(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        log.error("clientid:{} secret:{}, the token is {}"
                , serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret(), resultBean.getData());
        this.clientToken = resultBean.getData();
    }

    public String getClientToken() {
        if (this.clientToken == null) {
            this.refreshClientToken();
        }
        return clientToken;
    }

    public List<String> getAllowedClient() {
        if (this.allowedClient == null) {
            this.refreshAllowedClient();
        }
        return allowedClient;
    }


}
