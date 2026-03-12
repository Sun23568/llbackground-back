package com.llback.api.app.setting.handler;

import com.llback.api.app.setting.dto.req.SaveSysSettingCmd;
import com.llback.common.util.AssertUtil;
import com.llback.core.setting.repository.SysSettingRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 保存全局系统配置 Handler
 */
@Component
@HandlerAcl("setting:save")
public class SaveSysSettingHandler implements Handler<Void, SaveSysSettingCmd> {

    @Autowired
    private SysSettingRepository sysSettingRepository;

    @Override
    public Void execute(SaveSysSettingCmd cmd) {
        AssertUtil.notEmpty(cmd.getCfgKey(), "配置Key不能为空");
        sysSettingRepository.saveOrUpdate(cmd.getCfgKey(), cmd.getCfgValue());
        return null;
    }
}
