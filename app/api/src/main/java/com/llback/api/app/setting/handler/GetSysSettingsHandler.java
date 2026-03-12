package com.llback.api.app.setting.handler;

import com.llback.api.app.setting.dto.req.GetSysSettingsReq;
import com.llback.core.setting.repository.SysSettingRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 查询全局系统配置 Handler
 */
@Component
@HandlerAcl("setting:get")
public class GetSysSettingsHandler implements Handler<Map<String, String>, GetSysSettingsReq> {

    @Autowired
    private SysSettingRepository sysSettingRepository;

    @Override
    public Map<String, String> execute(GetSysSettingsReq req) {
        return sysSettingRepository.findAll();
    }
}
