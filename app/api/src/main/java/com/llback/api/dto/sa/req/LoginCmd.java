package com.llback.api.dto.sa.req;

import com.llback.frame.dto.Command;
import lombok.Data;

/**
 * 登录命令
 *
 * @author yz.sun
 * @date 2025/7/14
 */
@Data
public class LoginCmd implements Command {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 密码
     */
    private String password;
}
