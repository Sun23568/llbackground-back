package com.llback.dal.menu.dao;

import com.llback.dal.menu.po.MenuPo;

import java.util.List;

/**
 * 菜单数据访问接口
 */
public interface MenuDao {
    /**
     * 查询用户菜单
     */
    List<MenuPo> queryUserMenus(String userId);

    /**
     * 查询所有菜单
     */
    List<MenuPo> queryAllMenus();

    /**
     * 获取菜单编码数量
     */
    int getMenuCodeCount(String menuCode);

    /**
     * 添加菜单
     */
    void addMenu(MenuPo menuPo);
}
