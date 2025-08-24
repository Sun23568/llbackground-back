package com.llback.api.app.user.handler;

import com.llback.api.app.user.dto.req.UpdateCurUserCmd;
import com.llback.common.types.EncryptedPassword;
import com.llback.common.types.StringId;
import com.llback.common.types.UserId;
import com.llback.common.types.UserName;
import com.llback.common.util.AssertUtil;
import com.llback.core.user.eo.UserEo;
import com.llback.core.user.repository.AvatarRepository;
import com.llback.core.user.repository.UserRepository;
import com.llback.frame.Handler;
import com.llback.frame.context.ReqContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 添加用户处理器
 */
@Component
public class UpdateCurUserHandler implements Handler<Void, UpdateCurUserCmd> {
    /**
     * 用户仓库
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * 头像仓储
     */
    @Autowired
    private AvatarRepository avatarRepository;

    @Override
    public Void execute(UpdateCurUserCmd cmd) {
        AssertUtil.notNull(cmd, "入参异常");

        // 根据userId查询用户
        UserEo user = userRepository.findUser(UserId.of(ReqContext.userSession().getUserId()));
        AssertUtil.assertTrue(user != null, "用户不存在");
        // 修改密码
        if (StringUtils.isNotEmpty(cmd.getOldPassword()) && StringUtils.isNotEmpty(cmd.getNewPassword())) {
            user.changePassword(EncryptedPassword.of(cmd.getOldPassword()), EncryptedPassword.of(cmd.getNewPassword()), EncryptedPassword.of(cmd.getConfirmPassword()));
        }
        // 修改用户名
        user.changeUserName(UserName.of(cmd.getUserName()));

        // 入库头像
        if (StringUtils.isNotEmpty(cmd.getAvatar())) {
            StringId avatarId = avatarRepository.addAvatar(cmd.getAvatar());
            // 修改用户信息
            user.changeAvatar(avatarId);
        }

        userRepository.updateUser(user);

        return Void.TYPE.cast(null);
    }
}
