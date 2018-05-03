package com.repository;

import com.base.vo.PermissionInfo;
import com.entity.StaffinfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<StaffinfoEntity,Integer>{

    public StaffinfoEntity findByUsername(String username);



}
