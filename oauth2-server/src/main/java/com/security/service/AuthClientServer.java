package com.security.service;

import java.util.List;

/**
 * @author fanzhenxing
 * @create 2018/4/27 9:16 AM
 */
public interface AuthClientServer {
    public String apply(String clientId,String secret) throws Exception;

    public List<String> getAllowedClient(String serviceId,String secret);

    public List<String> getAllowedClient(String serviceId);

    public void registeryClient();

    public void validate(String clientId,String secret) throws Exception;
}
