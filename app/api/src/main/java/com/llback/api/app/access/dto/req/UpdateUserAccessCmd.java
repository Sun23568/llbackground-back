package com.llback.api.app.access.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

import java.util.List;

/**
 * 更新用户权限命令
 */
@Data
public class UpdateUserAccessCmd implements Command{
    /**
     * 权限列表
     */
    private List<String> permissions;

    /**
     * 菜单列表
     */
    private List<String> menuIds;

    /**
     * 用户ID
     */
    private String userId;
}
