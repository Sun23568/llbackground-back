package com.llback.core.setting.repository;

import java.util.Map;

/**
 * 全局系统配置仓储接口
 */
public interface SysSettingRepository {

    /**
     * 查询所有配置，返回 key->value 的 Map
     */
    Map<String, String> findAll();

    /**
     * 保存或更新单个配置
     *
     * @param key   配置 key
     * @param value 配置 value
     */
    void saveOrUpdate(String key, String value);
}
