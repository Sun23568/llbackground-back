package com.llback.api.app.sa.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

/**
 * 登出命令
 *
 * @author yz.sun
 * @date 2025/7/14
 */
@Data
public class LogoutCmd implements Command {
    public static final LogoutCmd EMPTY = new LogoutCmd();
}
