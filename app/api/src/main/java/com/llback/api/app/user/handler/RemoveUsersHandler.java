package com.llback.api.app.user.handler;

import com.llback.api.app.user.dto.req.RemoveUsersCmd;
import com.llback.base.user.repository.UserRepository;
import com.llback.common.util.AssertUtil;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
