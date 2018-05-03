package com.security.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author fanzhenxing
 * @create 2018/4/28 11:11 AM
 */
@Configuration
@Data
public class ClientConfiguration {
    @Value("${client.id}")
    private String clientId;
    @Value("${client.secret}")
    private String clientSecret;
    @Value(("${client.token-header}"))
    private String clientTokenHeader;

}
