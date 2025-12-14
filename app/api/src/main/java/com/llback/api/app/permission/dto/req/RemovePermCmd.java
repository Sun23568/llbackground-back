package com.llback.api.app.permission.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

/**
 * 删除权限CMD
 */
@Data
public class RemovePermCmd implements Command {
    /**
     * 权限ID
     */
    private String permId;
}
