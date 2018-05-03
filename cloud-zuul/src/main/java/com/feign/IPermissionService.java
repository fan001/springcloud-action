package com.feign;

import com.base.vo.PermissionInfo;
import com.base.vo.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author fanzhenxing
 * @create 2018/4/26 9:05 AM
 */
@FeignClient(value = "cloud-admin")
public interface IPermissionService {

    @RequestMapping(value = "/api/user/{username}/permissions",method = RequestMethod.GET)
    public List<PermissionInfo> getPermissionByUsername(@PathVariable("username") String username);


    @RequestMapping(value = "/api/permissions",method = RequestMethod.GET)
    public List<PermissionInfo> getAllPermissionInfo();

}
