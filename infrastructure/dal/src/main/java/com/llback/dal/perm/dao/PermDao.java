package com.llback.dal.perm.dao;

import com.llback.dal.perm.po.PermissionPo;

import java.util.List;

/**
 * 权限数据访问接口
 */
public interface PermDao {
    /**
     * 查询用户权限
     */
    List<PermissionPo> queryUserPerms(String userId);

    /**
     * 查询所有权限
     */
    List<PermissionPo> queryAllPermmission();

    /**
     * 查询权限编码数量
     */
    int getPermCodeCount(String permCode);

    /**
     * 添加权限
     */
    void addPermission(PermissionPo permissionPo);
}
