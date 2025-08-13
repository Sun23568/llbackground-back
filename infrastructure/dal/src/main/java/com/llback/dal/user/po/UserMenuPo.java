package com.llback.dal.user.po;

import com.llback.dal.menu.po.MenuPo;
import lombok.Data;

import java.util.List;

/**
 * 用户菜单Po
 */
@Data
public class UserMenuPo {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 菜单列表
     */
    private List<MenuPo> menus;
}
