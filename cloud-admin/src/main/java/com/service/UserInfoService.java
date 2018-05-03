package com.service;

import com.base.vo.PermissionInfo;
import com.base.vo.UserInfo;
import com.entity.PermissionEntity;
import com.entity.RolePurviewEntity;
import com.entity.StaffinfoEntity;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.repository.PermissionRepository;
import com.repository.UserInfoRepository;
import com.vo.ViewUserInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserInfoService {


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    PermissionRepository permissionRepository;

    public ViewUserInfo getUserInfo(String username) {
        StaffinfoEntity staffinfoEntity = userInfoRepository.findByUsername(username);
        ViewUserInfo viewUserInfo = new ViewUserInfo();
        BeanUtils.copyProperties(staffinfoEntity, viewUserInfo);
        List<PermissionInfo> permissionInfos = Lists.newArrayList();
        Map<String, PermissionInfo> all_menu = Maps.newHashMap();
        List<PermissionInfo> menus = Lists.newArrayList();
        final List<PermissionInfo> opt_permission = new ArrayList<>();
        staffinfoEntity.getRoles().forEach(e -> {
            List<RolePurviewEntity> list = e.getRolePurviewEntities();
            list.forEach(d -> {
                PermissionInfo permissionInfo = new PermissionInfo();
                BeanUtils.copyProperties(d.getPermissionEntity(), permissionInfo);
                if (permissionInfo.getType().equals("00")) {
                    all_menu.put(permissionInfo.getId() + "", permissionInfo);
                } else {
                    opt_permission.add(permissionInfo);
                }
            });
        });

        all_menu.forEach((k, v) -> {
            if (v.getParentId() > 0) {
                PermissionInfo parentMenu = all_menu.get(v.getParentId() + "");
                List<PermissionInfo> children = parentMenu.getPermissionInfos()
                        == null ? Lists.newArrayList() : parentMenu.getPermissionInfos();
                children.add(v);
                parentMenu.setPermissionInfos(children);
            } else {
                menus.add(v);
            }
        });

        List ropt_permission = opt_permission.stream().distinct().collect(Collectors.toList());
        viewUserInfo.setMenuList(menus);
        viewUserInfo.setPermissionInfoList(ropt_permission);
        return viewUserInfo;

    }

    public List<PermissionInfo> getPermissionByUsername(String username){
        List<PermissionEntity> list = permissionRepository.findAll();
        System.out.println(list.size());
        return null;

    }

    public List<PermissionInfo> findPermissionByUsername(String usename){
        ViewUserInfo viewUserInfo = getUserInfo(usename);
        List<PermissionInfo> permissionInfos = viewUserInfo.getPermissionInfoList();
        return permissionInfos;
    }

    public List<PermissionInfo> findAllPermission(){
        List<PermissionInfo> permissionEntities = permissionRepository.findAllPermissionses();
        return permissionEntities;
    }

    public UserInfo validate(String username,String password){
        UserInfo info = new UserInfo();
        StaffinfoEntity staffinfoEntity = userInfoRepository.findByUsername(username);
        if(encoder.matches(password,staffinfoEntity.getPassword())){
            BeanUtils.copyProperties(staffinfoEntity,info);
            info.setId(staffinfoEntity.getId() + "");
        }
        return info;

    }


}
