package com.rpc;

import com.base.vo.PermissionInfo;
import com.base.vo.UserInfo;
import com.netflix.discovery.converters.Auto;
import com.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author fanzhenxing
 * @create 2018/4/25 4:29 PM
 */
@RestController
@RequestMapping("/api")
public class UserRest {

    @Autowired
    UserInfoService userInfoService;

    @RequestMapping(value = "/user/validate", method = RequestMethod.POST)
    public UserInfo validate(@RequestBody Map<String, String> body) {
        return userInfoService.validate(body.get("username"), body.get("password"));
    }

    @RequestMapping(value = "/user/{username}/permissions", method = RequestMethod.GET)
    public List<PermissionInfo> getPermissionByUsername(@PathVariable("username") String username) {
        List<PermissionInfo> permissionInfos = userInfoService.getPermissionByUsername(username);

        return permissionInfos;
    }


    @RequestMapping(value = "/permissions", method = RequestMethod.GET)
    public List<PermissionInfo> getAllPermissions() {

        return userInfoService.findAllPermission();

    }


}
