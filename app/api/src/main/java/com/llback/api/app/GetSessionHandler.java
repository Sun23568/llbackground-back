package com.llback.api.app;

import com.llback.api.dto.sa.req.GetSessionReq;
import com.llback.api.dto.sa.resp.UserSessionResp;
import com.llback.frame.Handler;
import org.springframework.stereotype.Component;

/**
 * 获取用户会话信息
 */
@Component
public class GetSessionHandler implements Handler<UserSessionResp, GetSessionReq> {
    @Override
    public UserSessionResp execute(GetSessionReq req) {
        return null;
    }
}
