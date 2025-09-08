package com.llback.api.app.user.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

import java.util.List;

/**
 * 删除用户
 */
@Data
public class RemoveUsersCmd implements Command {
    /**
     * 用户ID列表
     */
    List<String> userIds;
}
