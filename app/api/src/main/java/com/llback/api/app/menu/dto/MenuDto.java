package com.llback.api.app.menu.dto;

import lombok.Data;

/**
 * 菜单DTO
 *
 * @author llback
 */
@Data
public class MenuDto {
    /**
     * 菜单ID
     */
    private String menuId;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String menuName;
}
