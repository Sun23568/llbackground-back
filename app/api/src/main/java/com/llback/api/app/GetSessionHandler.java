package com.llback.api.app;

import com.llback.api.dto.sa.req.GetSessionReq;
import com.llback.api.dto.sa.resp.UserSessionResp;
import com.llback.common.service.CacheService;
import com.llback.common.types.CacheType;
import com.llback.frame.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * 获取用户会话信息
 */
@Primary
@Component
public class GetSessionHandler implements Handler<UserSessionResp, GetSessionReq> {
    @Autowired
    private CacheService cacheService;
    @Override
    public UserSessionResp execute(GetSessionReq req) {
        cacheService.getCacheObject(CacheType.USER, "12312312");
        return null;
    }
}
