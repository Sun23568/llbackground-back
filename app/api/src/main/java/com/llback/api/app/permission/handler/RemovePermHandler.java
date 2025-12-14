package com.llback.api.app.permission.handler;

import com.llback.api.app.permission.dto.req.RemovePermCmd;
import com.llback.common.util.AssertUtil;
import com.llback.core.perm.repository.PermRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 删除权限处理器
 */
@Component
@HandlerAcl("perm:remove")
public class RemovePermHandler implements Handler<Void, RemovePermCmd> {
    /**
     * 权限仓储
     */
    @Autowired
    private PermRepository permRepository;

    @Override
    public Void execute(RemovePermCmd cmd) {
        // 校验
        AssertUtil.notEmpty(cmd.getPermId(), "权限ID不能为空");

        // 删除权限（含级联删除用户关联）
        permRepository.deletePerm(cmd.getPermId());
        return null;
    }
}
