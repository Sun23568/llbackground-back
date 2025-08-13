package com.llback.api.app.sa.handler;

import com.llback.api.app.sa.dto.req.GetSessionReq;
import com.llback.api.app.sa.dto.resp.UserSessionResp;
import com.llback.frame.Handler;
import com.llback.frame.context.ReqContext;
import org.springframework.stereotype.Component;

/**
 * 获取用户会话信息
 */
@Component
public class GetSessionHandler implements Handler<UserSessionResp, GetSessionReq> {
    @Override
    public UserSessionResp execute(GetSessionReq req) {
        return UserSessionResp.builder().userSession(ReqContext.userSession()).build();
    }
}
