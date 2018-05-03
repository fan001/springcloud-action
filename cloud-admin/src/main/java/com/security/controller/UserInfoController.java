package com.security.controller;

import com.base.bean.ResultBean;
import com.service.UserInfoService;
import com.vo.ViewUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;

    @GetMapping(value = "/{username}")
    public ResultBean<ViewUserInfo> loadViewUserinfo(@PathVariable String username){
        ResultBean<ViewUserInfo> resultBean = new ResultBean<>(userInfoService.getUserInfo(username));
        return resultBean;

    }


}
