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
    List<PermissionPo> queryAllPermission();

    /**
     * 查询权限编码数量
     */
    int getPermCodeCount(String permCode);

    /**
     * 添加权限
     */
    void addPermission(PermissionPo permissionPo);

    /**
     * 更新权限
     */
    void updatePermission(PermissionPo permissionPo);

    /**
     * 删除权限
     */
    void deletePermission(String permId);

    /**
     * 删除用户权限关联（级联删除）
     */
    void deleteUserPermissions(String permId);

    /**
     * 分页查询权限列表
     *
     * @param permType 权限类型（可选，为null或空字符串时查询所有）
     */
    List<PermissionPo> queryPermPage(String permType);
}
