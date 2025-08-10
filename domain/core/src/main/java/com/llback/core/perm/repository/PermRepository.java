package com.llback.core.perm.repository;

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
}
