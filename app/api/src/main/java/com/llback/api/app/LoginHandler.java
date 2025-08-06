package com.llback.api.app;

import com.llback.api.dto.sa.req.LoginCmd;
import com.llback.api.dto.sa.resp.LoginResp;
import com.llback.common.types.UserId;
import com.llback.common.util.AssertUtil;
import com.llback.core.user.repository.UserRepository;
import com.llback.core.user.eo.UserEo;
import com.llback.frame.Handler;
import com.llback.frame.PubAcl;
import com.llback.frame.context.ReqContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 登录处理
 *
 * @author yz.sun
 * @date 2025/7/14
 */
@PubAcl
@Component
public class LoginHandler implements Handler<LoginResp, LoginCmd> {

    @Value("${sa.private-key}")
    private String privateKey;

    @Autowired
    private UserRepository userRepository;

    @Override
    public LoginResp execute(LoginCmd req) {
        // 校验
        AssertUtil.notEmpty(req.getUserId(), "用户ID不能为空");
        AssertUtil.notEmpty(req.getPassword(), "密码不能为空");
        // 根据用户获取用户信息
        UserEo user = userRepository.findUser(UserId.of(req.getUserId()));
        // 验证密码
        AssertUtil.assertTrue(user != null, "密码或用户名错误错误");
        AssertUtil.assertTrue(user.checkPassword(req.getPassword()), "密码或用户名错误错误");
        // 登录
        ReqContext context = ReqContext.getCurrent();
        context.createSession(user.getUserId());
        return null;
    }
}
