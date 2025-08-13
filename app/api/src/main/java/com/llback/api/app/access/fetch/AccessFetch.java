package com.llback.api.app.access.fetch;

import com.llback.api.app.access.dto.UserAccessDto;

import java.util.List;

/**
 * 权限FETCH
 */
public interface AccessFetch {
    /**
     * 查询所有用户权限
     */
    List<UserAccessDto> queryAllUserPerms();

    /**
     * 查询所有用户菜单
     */
    List<UserAccessDto> queryAllUserMenus();
}
