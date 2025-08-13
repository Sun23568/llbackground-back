package com.llback.api.app.sa.dto.resp;

import com.llback.frame.context.UserSession;
import lombok.Builder;
import lombok.Getter;

/**
 * 登录响应
 */
@Getter
@Builder
public class LoginResp {
    /**
     * 用户会话
     */
    private UserSession userSession;
}
