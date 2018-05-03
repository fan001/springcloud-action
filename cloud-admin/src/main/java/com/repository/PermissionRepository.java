package com.repository;

import com.base.vo.PermissionInfo;
import com.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author fanzhenxing
 * @create 2018/4/26 1:08 PM
 */
public interface PermissionRepository extends JpaRepository<PermissionEntity,Integer> {

    @Query(value = "select new com.base.vo.PermissionInfo(id,code,name,url,iconCls,isDisplay,parentId,type) from  PermissionEntity a")
    public List<PermissionInfo> findAllPermissionses();
}
