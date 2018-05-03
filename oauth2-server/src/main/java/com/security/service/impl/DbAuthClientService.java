package com.security.service.impl;

import com.base.exception.auth.ClientInvalidException;
import com.base.util.UUIDUtils;
import com.base.vo.ClientInfo;
import com.google.common.collect.Lists;
import com.security.entity.Client;
import com.security.repository.ClientRepository;
import com.security.service.AuthClientServer;
import com.security.util.ClientTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fanzhenxing
 * @create 2018/4/27 9:19 AM
 */
@Service
@Slf4j
@EnableScheduling
public class DbAuthClientService implements AuthClientServer {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientTokenUtil clientTokenUtil;

    @Autowired
    private DiscoveryClient discoveryClient;


    private ApplicationContext context;

    @Autowired
    public DbAuthClientService(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public String apply(String clientId, String secret) throws Exception {
        Client client = getClient(clientId, secret);
        return clientTokenUtil.generateToken(new ClientInfo(client.getCode(), client.getName(), client.getId().toString()));
    }

    @Override
    public List<String> getAllowedClient(String serviceId, String secret) {
        Client info = clientRepository.findByCodeAndSecret(serviceId, secret);
        List<String> clientCodes = Lists.newArrayList();
        info.getClientServices().forEach(e -> clientCodes.add(e.getClient().getCode()));

        return clientCodes;
    }

    @Override
    public List<String> getAllowedClient(String serviceId) {
        Client client = clientRepository.findByCode(serviceId);
        List<String> clientCodes = Lists.newArrayList();
        client.getClientServices().forEach(e -> clientCodes.add(e.getClient().getCode()));
        return clientCodes;
    }

    @Override
    @Scheduled(cron = "0 0/1 * * * ?")
    public void registeryClient() {
        log.error("begin to auto register client ...............");
        discoveryClient.getServices().forEach((name) -> {
            Client client = clientRepository.findByCode(name);
            if (client == null) {
                log.error("register client[ "+name+" ]  ...............");

                Client newClient = new Client();
                newClient.setName(name);
                newClient.setCode(name);
                newClient.setSecret(UUIDUtils.generateShortUuid());
                clientRepository.save(newClient);
            }

        });

    }

    @Override
    public void validate(String clientId, String secret) throws Exception {
        Client client = clientRepository.findByCodeAndSecret(clientId, secret);
        if (client == null) {
            throw new ClientInvalidException("Client not found or Client secret is error");
        }

    }

    private Client getClient(String clientId, String secret) {

        Client client = clientRepository.findByCode(clientId);
        if (client == null || !client.getSecret().equals(secret)) {
            throw new ClientInvalidException("client not found or client secret is error");
        }
        return client;
    }


}
