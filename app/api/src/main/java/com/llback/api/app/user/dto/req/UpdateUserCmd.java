package com.llback.api.app.user.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

/**
 * 添加用户
 */
@Data
public class UpdateUserCmd implements Command {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 确认密码
     */
    private String confirmPassword;
}
