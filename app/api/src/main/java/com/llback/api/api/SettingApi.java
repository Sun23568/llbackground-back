package com.llback.api.api;

import com.llback.api.app.setting.dto.req.GetSysSettingsReq;
import com.llback.api.app.setting.dto.req.SaveSysSettingCmd;
import com.llback.frame.rest.RestApi;
import com.llback.frame.rest.RestResult;
import org.springframework.web.bind.annotation.*;

/**
 * 全局系统配置接口
 */
@RestController
@RequestMapping("/setting")
public class SettingApi implements RestApi {

    /**
     * 查询所有配置
     */
    @GetMapping("/all")
    public RestResult<?> getAll() {
        return this.execute(new GetSysSettingsReq());
    }

    /**
     * 保存或更新单个配置
     */
    @PostMapping("/save")
    public RestResult<?> save(@RequestBody SaveSysSettingCmd cmd) {
        return this.execute(cmd);
    }
}
