package com.llback.core.menu.repository;

import com.llback.common.types.UserId;
import com.llback.core.menu.eo.MenuEo;

import java.util.List;

/**
 * 权限仓储
 */
public interface MenuRepository {
    /**
     * 查询用户菜单列表
     */
    List<MenuEo> queryUserMenus(UserId userId);

    /**
     * 查询所有菜单
     */
    List<MenuEo> getAllMenus();

    /**
     * 添加菜单
     */
    void addMenu(MenuEo menuEo);
}
