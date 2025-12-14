package com.llback.api.app.menu.handler;

import com.llback.api.app.menu.dto.req.RemoveMenuCmd;
import com.llback.common.util.AssertUtil;
import com.llback.core.menu.repository.MenuRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 删除菜单处理器
 */
@Component
@HandlerAcl("menu:remove")
public class RemoveMenuHandler implements Handler<Void, RemoveMenuCmd> {
    /**
     * 菜单仓储接口
     */
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public Void execute(RemoveMenuCmd cmd) {
        AssertUtil.notEmpty(cmd.getMenuId(), "菜单ID不能为空");
        menuRepository.deleteMenu(cmd.getMenuId());
        return null;
    }
}
