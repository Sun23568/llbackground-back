package com.llback.dal.ai.repository;

import com.llback.api.app.ai.dto.AiConfigDto;
import com.llback.api.app.ai.fetch.AiConfigFetch;
import com.llback.common.types.StringId;
import com.llback.core.ai.eo.AiConfigEo;
import com.llback.core.ai.repository.AiConfigRepository;
import com.llback.core.ai.repository.AiModelRepository;
import com.llback.dal.ai.dao.AiDao;
import com.llback.dal.ai.po.AiConfigPo;
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
public class AiModelConfigRepositoryImpl implements AiModelRepository, AiConfigFetch, AiConfigRepository {
    /**
     * AI模块仓储
     */
    @Autowired
    private AiDao aiDao;

    /**
     * 新增AI配置
     */
    @Override
    public void addAiConfig(AiConfigEo aiConfigEo) {
        AiConfigPo po = PoAssembleUtil.eo2Po(aiConfigEo, AiConfigPo.class);
        aiDao.insertAiConfig(po);
    }

    /**
     * 更新AI配置
     */
    @Override
    public void updateAiConfig(AiConfigEo aiConfigEo) {
        AiConfigPo po = PoAssembleUtil.eo2Po(aiConfigEo, AiConfigPo.class);
        aiDao.updateAiConfig(po);
    }

    /**
     * 根据菜单代码查询AI配置
     */
    @Override
    public AiConfigEo queryByMenuCode(String menuCode) {
        AiConfigPo po = aiDao.queryConfigByMenuCode(menuCode);
        return PoAssembleUtil.po2Eo(po, AiConfigEo.class);
    }

    /**
     * 查询所有AI配置列表
     */
    @Override
    public List<AiConfigEo> queryAllConfigs() {
        List<AiConfigPo> poList = aiDao.queryAllConfig();
        return PoAssembleUtil.poList2EoList(poList, AiConfigEo.class);
    }

    /**
     * 更新背景图片
     */
    @Override
    public void updateBackgroundImage(StringId aiMenuCode, StringId backgroundImageId) {
        aiDao.updateBackgroundImage(aiMenuCode.toString(), backgroundImageId.toString());
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
