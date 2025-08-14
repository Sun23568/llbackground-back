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

    /**
     * 删除用户权限
     *
     * @author yz.sun
     * @date 2025/8/14
     */
    void removeUserPerms(String userId);

    /**
     * 删除用户菜单
     *
     * @author yz.sun
     * @date 2025/8/14
     */
    void removeUserMenus(String userId);

    /**
     * 添加用户权限
     */
    void addUserPerms(String userId, List<String> permissions);

    /**
     * 添加用户菜单
     */
    void addUserMenus(String userId, List<String> menuIds);
}
