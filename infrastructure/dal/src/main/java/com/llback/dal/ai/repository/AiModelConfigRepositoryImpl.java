package com.llback.dal.ai.repository;

import com.llback.api.app.ai.dto.AiConfigDto;
import com.llback.api.app.ai.fetch.AiConfigFetch;
import com.llback.common.types.StringId;
import com.llback.core.ai.repository.AiModelRepository;
import com.llback.dal.ai.dao.AiDao;
import com.llback.rt.common.util.PoAssembleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * AI模块仓储实现类
 *
 * @author yz.sun
 * @date 2025/10/6
 */
@Component
public class AiModelConfigRepositoryImpl implements AiModelRepository, AiConfigFetch {
    /**
     * AI模块仓储
     */
    @Autowired
    private AiDao aiDao;

    /**
     * 更新背景图片
     */
    @Override
    public void updateBackground(StringId aiMenuId, StringId backgroundId) {
        // 先删后增
        aiDao.deleteBackground(aiMenuId.toString());
        aiDao.addBackground(aiMenuId.toString(), backgroundId.toString());
    }

    /**
     * 查询AI菜单背景图片
     *
     * @param aiMenuId
     */
    @Override
    public AiConfigDto queryAiConfig(StringId aiMenuId) {
        return PoAssembleUtil.poToDto(aiDao.queryConfig(aiMenuId.toString()), AiConfigDto.class);
    }

    /**
     * 查询AI菜单配置列表
     */
    @Override
    public List<AiConfigDto> queryAllAiConfigList() {
        return PoAssembleUtil.poList2DtoList(aiDao.queryAllConfig(), AiConfigDto.class);
    }
}
