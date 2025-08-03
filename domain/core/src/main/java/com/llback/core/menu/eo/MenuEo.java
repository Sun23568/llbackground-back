package com.llback.core.menu.eo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuEo {
    /**
     * 菜单ID
     */
    private String menuId;

    /**
     * 菜单代码
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 是否是菜单 0否(目录) 1是
     */
    private String isMenu;

    /**
     * 父菜单
     */
    private String parentMenu;

    /**
     * url
     */
    private String url;
}
