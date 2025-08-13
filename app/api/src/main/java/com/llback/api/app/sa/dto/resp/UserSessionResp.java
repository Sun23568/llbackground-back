package com.llback.api.app.sa.dto.resp;

import com.llback.frame.context.UserSession;
import lombok.Builder;
import lombok.Getter;

/**
 * 用户会话信息
 */
@Getter
@Builder
public class UserSessionResp {
    /**
     * 用户会话信息
     */
    private UserSession userSession;
}
