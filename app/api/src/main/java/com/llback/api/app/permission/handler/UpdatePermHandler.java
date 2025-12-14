package com.llback.api.app.permission.handler;

import com.llback.api.app.permission.dto.req.UpdatePermCmd;
import com.llback.common.types.FuncCode;
import com.llback.common.util.AssertUtil;
import com.llback.core.perm.eo.FuncPermEo;
import com.llback.core.perm.repository.PermRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 更新权限处理器
 */
@Component
@HandlerAcl("perm:update")
public class UpdatePermHandler implements Handler<Void, UpdatePermCmd> {
    /**
     * 权限仓储
     */
    @Autowired
    private PermRepository permRepository;

    @Override
    public Void execute(UpdatePermCmd cmd) {
        // 校验
        AssertUtil.notEmpty(cmd.getPermId(), "权限ID不能为空");
        AssertUtil.notEmpty(cmd.getPermCode(), "权限编码不能为空");
        AssertUtil.notEmpty(cmd.getPermName(), "权限名称不能为空");
        AssertUtil.notEmpty(cmd.getPermType(), "权限类型不能为空");

        // 构建更新实体对象
        FuncPermEo permEo = FuncPermEo.builder()
                .permId(cmd.getPermId())
                .permCode(FuncCode.of(cmd.getPermCode()))
                .permName(cmd.getPermName())
                .type(cmd.getPermType())
                .build();

        // 更新权限
        permRepository.updatePerm(permEo);
        return null;
    }
}
