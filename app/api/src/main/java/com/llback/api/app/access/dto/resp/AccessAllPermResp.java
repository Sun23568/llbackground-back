package com.llback.api.app.access.dto.resp;

import com.llback.api.app.access.dto.PermTypeDto;
import com.llback.api.app.menu.dto.MenuDto;
import lombok.Data;

import java.util.List;

/**
 * 获取所有权限返回
 */
@Data
public class AccessAllPermResp {
    /**
     * 权限列表
     */
    private List<PermTypeDto> permTypes;

    /**
     * 菜单列表
     */
    private List<MenuDto> menus;
}
