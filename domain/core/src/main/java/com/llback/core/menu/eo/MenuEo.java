package com.llback.core.menu.eo;

import com.llback.common.types.MenuCode;
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
    private MenuCode menuCode;

    /**
     * 菜单名称
     */
    private String menuName;
}
