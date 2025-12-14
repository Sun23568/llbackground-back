package com.llback.api.app.menu.fetch;

import com.llback.api.app.menu.dto.MenuDto;

/**
 * 菜单数据获取接口
 *
 * @author llback
 */
public interface MenuFetch {
    /**
     * 根据菜单代码查询菜单
     *
     * @param menuCode 菜单代码
     * @return 菜单DTO
     */
    MenuDto queryMenuByCode(String menuCode);

    /**
     * 获取菜单编码数量
     *
     * @param menuCode 菜单编码
     * @return 数量
     */
    int getMenuCodeCount(String menuCode);
}
