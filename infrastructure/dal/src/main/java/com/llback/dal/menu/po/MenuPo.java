
package com.llback.dal.menu.po;

import lombok.Data;

/**
 * 菜单持久化对象
 */
@Data
public class MenuPo {
    /**
     * 菜单ID，唯一标识一个菜单项
     */
    private String menuId;

    /**
     * 菜单代码，唯一标识一个菜单项
     */
    private String menuCode;

    /**
     * 菜单名称，展示在界面上的菜单文字
     */
    private String menuName;
}
