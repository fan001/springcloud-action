package com.configure;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;

/**
 * @author fanzhenxing
 * @create 2018/5/22 2:52 PM
 */
@Component
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Override
    public List<SwaggerResource> get() {
        List<String> serviceList = discoveryClient.getServices();
        List<SwaggerResource> swaggerResourceList = Lists.newArrayList();
        serviceList.forEach(e->{
            ServiceInstance serviceInstance = loadBalancerClient.choose(e);
            swaggerResourceList.add(swaggerResource(e,"/api/" + e + "/v2/api-docs","2.0"));
        });
        return swaggerResourceList;
    }

    private SwaggerResource swaggerResource(String name,String location,String version){
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
