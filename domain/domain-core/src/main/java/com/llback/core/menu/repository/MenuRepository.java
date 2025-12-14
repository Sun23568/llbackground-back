package com.llback.core.menu.repository;

import com.github.pagehelper.PageInfo;
import com.llback.common.types.UserId;
import com.llback.core.menu.eo.MenuEo;

import java.util.List;

/**
 * 菜单仓储
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

    /**
     * 更新菜单
     */
    void updateMenu(MenuEo menuEo);

    /**
     * 删除菜单（含级联删除用户关联）
     */
    void deleteMenu(String menuId);

    /**
     * 分页查询菜单列表
     *
     * @param pageNum 页码
     * @param pageRow 每页数量
     */
    PageInfo<MenuEo> queryMenuPage(int pageNum, int pageRow);
}
