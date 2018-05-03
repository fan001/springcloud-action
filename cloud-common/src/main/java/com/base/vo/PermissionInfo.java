package com.base.vo;

import lombok.Data;

import java.util.List;

@Data
public class PermissionInfo {

    private Integer id;
    private String code;
    private String name;
    private String url;
    private String iconCls;

    private Boolean isDisplay;

    private Integer parentId;
    private String type; // 00 表示模块显示, 01 标识操作权限
    private List<PermissionInfo> permissionInfos;

    public PermissionInfo() {

    }

    public PermissionInfo(Integer id, String code, String name, String url, String iconCls, Boolean isDisplay, Integer parentId, String type) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.url = url;
        this.iconCls = iconCls;
        this.isDisplay = isDisplay;
        this.parentId = parentId;
        this.type = type;
    }
}
