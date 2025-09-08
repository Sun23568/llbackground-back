package com.llback.api.app.user.handler;

import com.llback.api.app.user.dto.req.RemoveUsersCmd;
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
import com.llback.frame.HandlerAcl;
import com.llback.frame.context.ReqContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 删除用户处理器
 */
@Component
@HandlerAcl("user:remove")
public class RemoveUsersHandler implements Handler<Void, RemoveUsersCmd> {
    /**
     * 用户仓库
     */
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Void execute(RemoveUsersCmd req) {
        AssertUtil.notEmptyList(req.getUserIds(), "用户ID不能为空");
        AssertUtil.assertTrue(userRepository.removeUsers(req.getUserIds()), "删除用户失败");
        return Void.TYPE.cast(null);
    }
}
