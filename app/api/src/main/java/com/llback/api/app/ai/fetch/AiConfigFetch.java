package com.llback.api.app.ai.fetch;

import com.llback.api.app.ai.dto.AiConfigDto;
import com.llback.common.types.StringId;

import java.util.List;

/**
 * AI模型数据获取接口
 */
public interface AiConfigFetch {
    /**
     * 查询AI菜单背景图片
     *
     * @param aiMenuId
     * @return
     */
    AiConfigDto queryAiConfig(StringId aiMenuId);

    /**
     * 查询所有AI菜单配置
     */
    List<AiConfigDto> queryAllAiConfigList();
}
