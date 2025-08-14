package com.llback.api.app.access.handler;

import com.llback.api.app.access.dto.req.UpdateUserAccessCmd;
import com.llback.api.app.access.fetch.AccessFetch;
import com.llback.common.util.AssertUtil;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 更新用户权限处理器
 */
@Component
@HandlerAcl("access:update")
public class UpdateUserAccessHandler implements Handler<Void, UpdateUserAccessCmd> {
    /**
     * 权限服务
     */
    @Autowired
    private AccessFetch accessFetch;

    @Override
    @Transactional
    public Void execute(UpdateUserAccessCmd req) {
        AssertUtil.assertFalse(StringUtils.isEmpty(req.getUserId()), "用户ID不能为空");
        AssertUtil.notEmptyList(req.getPermissions(), "权限不能为空");
        AssertUtil.notEmptyList(req.getMenuIds(), "菜单不能为空");
        // 删除用户下所有权限
        accessFetch.removeUserPerms(req.getUserId());
        // 删除用户下所有菜单
        accessFetch.removeUserMenus(req.getUserId());
        // 新增用户权限
        accessFetch.addUserPerms(req.getUserId(), req.getPermissions());
        // 新增用户菜单
        accessFetch.addUserMenus(req.getUserId(), req.getMenuIds());
        return null;
    }
}
