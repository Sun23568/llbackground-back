package com.llback.api.app;

import com.llback.api.dto.sa.req.LoginCmd;
import com.llback.api.dto.sa.resp.LoginResp;
import com.llback.common.types.UserId;
import com.llback.common.util.AssertUtil;
import com.llback.core.user.eo.UserEo;
import com.llback.core.user.repository.UserRepository;
import com.llback.frame.Handler;
import com.llback.frame.PubAcl;
import com.llback.frame.context.ReqContext;
import com.llback.frame.context.SessionMap;
import com.llback.frame.context.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录处理
 *
 * @author yz.sun
 * @date 2025/7/14
 */
@Slf4j
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
        Map<String, Object> extData = new HashMap<>();
        extData.put(SessionMap.TOKEN_CRT_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        UserSession session = context.createSession(user.getUserId(), extData);
        log.info("创建Session成功，用户ID：{}", user.getUserId().toString());
        return LoginResp.builder().userSession(session).build();
    }
}
