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

}
