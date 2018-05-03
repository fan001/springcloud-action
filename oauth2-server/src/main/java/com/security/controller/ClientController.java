package com.security.controller;

import com.base.bean.ResultBean;
import com.security.configuration.KeyConfiguration;
import com.security.service.AuthClientServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fanzhenxing
 * @create 2018/4/27 9:15 AM
 */
@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private AuthClientServer authClientServer;

    @Autowired
    private KeyConfiguration keyConfiguration;

    @RequestMapping(value = "/token",method = RequestMethod.POST)
    public ResultBean getAccessToken(String clientId,String secret) throws Exception{
        String token =authClientServer.apply(clientId,secret);
        return new ResultBean<String>(token);
    }

    @RequestMapping("/myClient")
    public ResultBean getAllowedClient(String serviceId,String secret) throws Exception{
       ResultBean<List<String>> resultBean =
               new ResultBean<>(authClientServer.getAllowedClient(serviceId,secret));
       return resultBean;
    }

    @RequestMapping(value = "/userPubKey",method = RequestMethod.POST)
    public ResultBean<byte[]> getServicePublicKey(String clientId,String secret)throws Exception{
        authClientServer.validate(clientId,secret);
        return new ResultBean<byte[]>(keyConfiguration.getUserPubKey());
    }


}
