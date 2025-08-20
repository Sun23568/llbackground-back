package com.llback.api.app.access.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

/**
 * 添加菜单命令
 */
@Data
public class AddMenuCmd implements Command {
    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String menuName;
}
