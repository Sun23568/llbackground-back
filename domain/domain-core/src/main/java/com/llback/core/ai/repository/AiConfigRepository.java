package com.llback.core.ai.repository;

import com.llback.core.ai.eo.AiConfigEo;

import java.util.List;

/**
 * AI配置仓储接口
 *
 * @author llback
 */
public interface AiConfigRepository {
    /**
     * 新增AI配置
     *
     * @param aiConfigEo AI配置实体
     */
    void addAiConfig(AiConfigEo aiConfigEo);

    /**
     * 更新AI配置
     *
     * @param aiConfigEo AI配置实体
     */
    void updateAiConfig(AiConfigEo aiConfigEo);

    /**
     * 根据菜单代码查询AI配置
     *
     * @param menuCode 菜单代码
     * @return AI配置实体
     */
    AiConfigEo queryByMenuCode(String menuCode);

    /**
     * 查询所有AI配置列表
     *
     * @return AI配置列表
     */
    List<AiConfigEo> queryAllConfigs();
}
