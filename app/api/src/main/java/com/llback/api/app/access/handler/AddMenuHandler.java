package com.llback.api.app.access.handler;

import com.llback.api.app.access.dto.req.AddMenuCmd;
import com.llback.api.app.access.fetch.AccessFetch;
import com.llback.common.types.MenuCode;
import com.llback.common.util.AssertUtil;
import com.llback.core.menu.eo.MenuEo;
import com.llback.core.menu.repository.MenuRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@HandlerAcl("access:addMenu")
public class AddMenuHandler implements Handler<Void, AddMenuCmd> {
    /**
     * 权限数据源
     */
    @Autowired
    private AccessFetch accessFetch;

    /**
     * 菜单仓储
     */
    @Autowired
    private MenuRepository menuRepository;

    /**
     * 添加权限
     */
    @Override
    public Void execute(AddMenuCmd cmd) {
        // 校验
        AssertUtil.notEmpty(cmd.getMenuCode(), "菜单编码不能为空");
        AssertUtil.notEmpty(cmd.getMenuName(), "菜单名称不能为空");
        AssertUtil.assertTrue(accessFetch.getMenuCodeCount(cmd.getMenuCode()) == 0, "菜单编码已存在");

        MenuEo menuEo = MenuEo.builder()
                .menuCode(MenuCode.of(cmd.getMenuCode()))
                .menuName(cmd.getMenuName())
                .build();
        menuRepository.addMenu(menuEo);
        return null;
    }
}
