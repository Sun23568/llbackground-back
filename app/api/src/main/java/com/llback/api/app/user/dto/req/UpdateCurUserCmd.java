package com.llback.api.app.user.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

/**
 * 添加用户
 */
@Data
public class UpdateCurUserCmd implements Command {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 确认密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 确认密码
     */
    private String confirmPassword;

    /**
     * 头像
     */
    private String avatar;
}
