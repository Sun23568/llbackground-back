package com.llback.api.app.setting.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

/**
 * 保存系统配置请求
 */
@Data
public class SaveSysSettingCmd implements Command {
    /**
     * 配置 key
     */
    private String cfgKey;
    /**
     * 配置 value
     */
    private String cfgValue;
}
