package com.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author fanzhenxing
 * @create 2018/5/8 10:16 AM
 */
@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
public class MonitorApplication {
    public static void main(String[] args) {

        SpringApplication.run(MonitorApplication.class, args);
    }


}
