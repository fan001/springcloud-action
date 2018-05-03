package com.security.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author fanzhenxing
 * @create 2018/4/28 12:25 PM
 */
@Configuration
@Data
public class UserConfiguration {
    @Value("{$jwt.token-header}")
    private String userTokenHeader;
}
