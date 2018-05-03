package com.client.feign;

import com.base.bean.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author fanzhenxing
 * @create 2018/4/26 3:46 PM
 */
@FeignClient("oauth-server")
public interface ServiceAuthFeign {

    @RequestMapping(value = "/client/myClient")
    public ResultBean<List<String>> getAllowedClient(@RequestParam("serviceId") String serviceId,
                                                     @RequestParam("secret") String secret);

    @RequestMapping(value = "/client/token",method = RequestMethod.POST)
    public ResultBean getAccessToken(@RequestParam("clientId") String clientId,@RequestParam("secret") String secret);

    @RequestMapping(value = "/client/servicePubKey",method = RequestMethod.POST)
    public ResultBean<byte[]> getServicePublicKey(@RequestParam("clientId") String clientId,@RequestParam("secret") String secret);

    @RequestMapping(value = "/client/userPubKey",method = RequestMethod.POST)
    public ResultBean<byte[]> getUserPublicKey(@RequestParam("clientId")String clientId,@RequestParam("secret") String secret);

}
