package com.llback.api.app.sa.handler;

import cn.dev33.satoken.stp.StpUtil;
import com.llback.api.app.sa.dto.req.LogoutCmd;
import com.llback.common.types.UserId;
import com.llback.frame.Handler;
import com.llback.frame.context.ReqContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 登出
 *
 * @author yz.sun
 * @date 2025/7/14
 */
@Slf4j
@Component
public class LogoutHandler implements Handler<Void, LogoutCmd> {

    @Override
    public Void execute(LogoutCmd req) {
        UserId userId = ReqContext.userSession().getUserId();
        StpUtil.logout(userId);
        return Void.TYPE.cast(null);
    }
}
