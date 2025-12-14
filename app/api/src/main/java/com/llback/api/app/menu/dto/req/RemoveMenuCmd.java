package com.llback.api.app.menu.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

/**
 * 删除菜单CMD
 */
@Data
public class RemoveMenuCmd implements Command {
    /**
     * 菜单ID
     */
    private String menuId;
}
