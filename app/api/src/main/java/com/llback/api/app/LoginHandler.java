package com.llback.api.app;

import com.llback.api.dto.sa.req.LoginCmd;
import com.llback.api.dto.sa.resp.LoginResp;
import com.llback.common.exception.BizException;
import com.llback.frame.Handler;
import org.springframework.stereotype.Component;

/**
 * 登录处理
 *
 * @author yz.sun
 * @date 2025/7/14
 */
@Component
public class LoginHandler implements Handler<LoginResp, LoginCmd> {
    @Override
    public LoginResp execute(LoginCmd req) {
        throw new BizException("请重新登录");
    }
}
