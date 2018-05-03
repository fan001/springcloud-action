package com.vo;

import com.base.vo.PermissionInfo;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ViewUserInfo  {

    private Integer id;
    private String username;
    private String name;
    private String description;
    private String status;
    private String phone;
    private String telephone;
    private String sex;
    private String identity;
    private String email;
    private String jobNo;
    private String address;
    private Date birthday;
    private Date lastSignDate;

    private List<PermissionInfo> menuList;
    private List<PermissionInfo> permissionInfoList;

}
