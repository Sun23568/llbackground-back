package com.llback.api.app.access.dto;

import com.llback.api.app.menu.dto.MenuDto;
import lombok.Data;

import java.util.List;

/**
 * 用户权限
 */
@Data
public class UserAccessDto {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 权限类型
     */
    private List<PermTypeDto> permTypes;

    /**
     * 菜单
     */
    private List<MenuDto> menus;
}
