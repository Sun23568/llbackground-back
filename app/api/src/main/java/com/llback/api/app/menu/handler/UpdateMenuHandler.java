package com.llback.api.app.menu.handler;

import com.llback.api.app.menu.dto.req.UpdateMenuCmd;
import com.llback.common.types.MenuCode;
import com.llback.common.util.AssertUtil;
import com.llback.core.menu.eo.MenuEo;
import com.llback.core.menu.repository.MenuRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 更新菜单处理器
 */
@Component
@HandlerAcl("menu:update")
public class UpdateMenuHandler implements Handler<Void, UpdateMenuCmd> {
    /**
     * 菜单仓储接口
     */
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public Void execute(UpdateMenuCmd cmd) {
        AssertUtil.notEmpty(cmd.getMenuId(), "菜单ID不能为空");
        AssertUtil.notEmpty(cmd.getMenuCode(), "菜单编码不能为空");
        AssertUtil.notEmpty(cmd.getMenuName(), "菜单名称不能为空");

        MenuEo menuEo = MenuEo.builder()
                .menuId(cmd.getMenuId())
                .menuCode(MenuCode.of(cmd.getMenuCode()))
                .menuName(cmd.getMenuName())
                .build();

        menuRepository.updateMenu(menuEo);
        return null;
    }
}
