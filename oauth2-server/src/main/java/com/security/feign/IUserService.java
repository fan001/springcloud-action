package com.security.feign;

import com.base.bean.JwtAuthenticationRequest;
import com.base.vo.UserInfo;
import com.security.configuration.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "cloud-admin",configuration = FeignConfiguration.class)
public interface IUserService {

    @RequestMapping(value = "/api/user/validate",method = RequestMethod.POST )
    public UserInfo validate(@RequestBody JwtAuthenticationRequest authenticationRequest);
}
