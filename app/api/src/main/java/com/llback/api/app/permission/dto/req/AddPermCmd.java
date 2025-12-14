package com.llback.api.app.permission.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

/**
 * 添加权限CMD
 */
@Data
public class AddPermCmd implements Command {
    /**
     * 权限编码
     */
    private String permCode;

    /**
     * 权限名称
     */
    private String permName;

    /**
     * 权限类型
     */
    private String permType;
}
