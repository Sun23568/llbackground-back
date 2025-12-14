package com.llback.api.app.menu.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

/**
 * 更新菜单CMD
 */
@Data
public class UpdateMenuCmd implements Command {
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
