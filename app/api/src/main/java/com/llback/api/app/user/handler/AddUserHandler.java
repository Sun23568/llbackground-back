package com.llback.api.app.user.handler;

import com.llback.api.app.user.dto.req.AddUserCmd;
import com.llback.common.types.EncryptedPassword;
import com.llback.common.types.UserId;
import com.llback.common.types.UserName;
import com.llback.common.util.AssertUtil;
import com.llback.core.user.eo.UserEo;
import com.llback.core.user.repository.UserRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 添加用户处理器
 */
@Component
@HandlerAcl("user:add")
public class AddUserHandler implements Handler<Void, AddUserCmd> {
    /**
     * 用户仓库
     */
    @Autowired
    private UserRepository userRepository;

    @Override
    public Void execute(AddUserCmd cmd) {
        AssertUtil.notNull(cmd, "入参异常");
        AssertUtil.notEmpty(cmd.getUserId(), "用户ID不能为空");
        AssertUtil.notEmpty(cmd.getUserName(), "用户名称不能为空");
        AssertUtil.notEmpty(cmd.getPassword(), "密码不能为空");
        AssertUtil.notEmpty(cmd.getConfirmPassword(), "确认密码不能为空");
        AssertUtil.assertEquals(cmd.getPassword(), cmd.getConfirmPassword(), "两次密码输入不一致");
        // 根据userId查询用户
        UserEo user = userRepository.findUser(UserId.of(cmd.getUserId()));
        AssertUtil.assertTrue(user == null || user.getUserId().isNullId(), "用户已存在");
        UserEo userEo = UserEo.builder()
                .userId(UserId.of(cmd.getUserId()))
                .userName(UserName.of(cmd.getUserName()))
                .password(EncryptedPassword.of(cmd.getPassword()))
                .build();
        AssertUtil.assertTrue(userRepository.addUser(userEo) == 1, "添加用户失败");
        return Void.TYPE.cast(null);
    }
}
