package com.llback.api.app.access.handler;

import com.llback.api.app.access.dto.req.UpdateUserAccessCmd;
import com.llback.common.util.AssertUtil;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

/**
 * 更新用户权限处理器
 */
@Component
@HandlerAcl("access:update")
public class UpdateUserAccessHandler implements Handler<Void, UpdateUserAccessCmd> {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    @Transactional
    public Void execute(UpdateUserAccessCmd req) {
        AssertUtil.assertTrue(StringUtils.isEmpty(req.getUserId()), "用户ID不能为空");
        return null;
    }
}
