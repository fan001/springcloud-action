package com.trace;

import org.aspectj.weaver.tools.Trace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin.server.EnableZipkinServer;

/**
 * @author fanzhenxing
 * @create 2018/5/23 1:34 PM
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZipkinServer
public class TraceApplication {

    public static void main(String[] args){
        SpringApplication.run(TraceApplication.class,args);
    }
}
