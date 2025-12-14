package com.llback.api.app.permission.handler;

import com.llback.api.app.permission.dto.req.AddPermCmd;
import com.llback.api.app.permission.fetch.PermissionFetch;
import com.llback.common.types.FuncCode;
import com.llback.common.util.AssertUtil;
import com.llback.core.perm.eo.FuncPermEo;
import com.llback.core.perm.repository.PermRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 添加权限处理器
 */
@Component
@HandlerAcl("perm:add")
public class AddPermHandler implements Handler<Void, AddPermCmd> {
    /**
     * 权限数据源
     */
    @Autowired
    private PermissionFetch permissionFetch;

    /**
     * 权限仓储
     */
    @Autowired
    private PermRepository permRepository;

    @Override
    public Void execute(AddPermCmd cmd) {
        // 校验
        AssertUtil.notEmpty(cmd.getPermCode(), "权限编码不能为空");
        AssertUtil.notEmpty(cmd.getPermName(), "权限名称不能为空");
        AssertUtil.notEmpty(cmd.getPermType(), "权限类型不能为空");
        AssertUtil.assertTrue(permissionFetch.getPermCodeCount(cmd.getPermCode()) == 0, "权限编码已存在");

        FuncPermEo permEo = FuncPermEo.builder()
                .permCode(FuncCode.of(cmd.getPermCode()))
                .permName(cmd.getPermName())
                .type(cmd.getPermType())
                .build();
        permRepository.addPerm(permEo);
        return null;
    }
}
