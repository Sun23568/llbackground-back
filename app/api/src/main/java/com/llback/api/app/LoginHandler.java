package com.llback.api.app;

import com.llback.api.dto.sa.req.LoginCmd;
import com.llback.api.dto.sa.resp.LoginResp;
import com.llback.frame.Handler;

/**
 * 登录处理
 *
 * @author yz.sun
 * @date 2025/7/14
 */
public class LoginHandler implements Handler<LoginResp, LoginCmd> {
    @Override
    public LoginResp execute(LoginCmd req) {
        return null;
    }
}
