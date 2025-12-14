package com.llback.core.perm.repository;

import com.github.pagehelper.PageInfo;
import com.llback.common.types.UserId;
import com.llback.core.perm.eo.FuncPermEo;

import java.util.List;

/**
 * 权限仓储
 */
public interface PermRepository {
    /**
     * 查询用户权限
     */
    List<FuncPermEo> queryUserPerms(UserId userId);

    /**
     * 查询所有权限
     */
    List<FuncPermEo> getAllPermission();

    /**
     * 添加权限 permEo
     */
    void addPerm(FuncPermEo permEo);

    /**
     * 更新权限
     */
    void updatePerm(FuncPermEo permEo);

    /**
     * 删除权限（含级联删除用户关联）
     */
    void deletePerm(String permId);

    /**
     * 分页查询权限列表
     *
     * @param pageNum 页码
     * @param pageRow 每页数量
     * @param permType 权限类型（可选，为null或空字符串时查询所有）
     */
    PageInfo<FuncPermEo> queryPermPage(int pageNum, int pageRow, String permType);
}
