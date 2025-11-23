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
import java.util.stream.Collectors;

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
        AiConfigPo po = eoToPo(aiConfigEo);
        aiDao.insertAiConfig(po);
    }

    /**
     * 更新AI配置
     */
    @Override
    public void updateAiConfig(AiConfigEo aiConfigEo) {
        AiConfigPo po = eoToPo(aiConfigEo);
        aiDao.updateAiConfig(po);
    }

    /**
     * 根据菜单代码查询AI配置
     */
    @Override
    public AiConfigEo queryByMenuCode(String menuCode) {
        AiConfigPo po = aiDao.queryConfigByMenuCode(menuCode);
        return poToEo(po);
    }

    /**
     * 查询所有AI配置列表
     */
    @Override
    public List<AiConfigEo> queryAllConfigs() {
        List<AiConfigPo> poList = aiDao.queryAllConfig();
        return poList.stream().map(this::poToEo).collect(Collectors.toList());
    }

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

    /**
     * EO转PO
     */
    private AiConfigPo eoToPo(AiConfigEo eo) {
        if (eo == null) {
            return null;
        }
        AiConfigPo po = new AiConfigPo();
        po.setPkId(eo.getPkId() != null ? eo.getPkId().toString() : null);
        po.setMenuId(eo.getMenuId() != null ? eo.getMenuId().toString() : null);
        po.setMenuName(eo.getMenuName());
        po.setMenuCode(eo.getMenuCode());
        po.setOllamaModelId(eo.getOllamaModelId());
        po.setComfyUiUrl(eo.getComfyUiUrl());
        po.setOllamaUrl(eo.getOllamaUrl());
        po.setComfyFileId(eo.getComfyFileId());
        po.setBackgroundImage(eo.getBackgroundImage());
        po.setContextSize(eo.getContextSize());
        po.setCreateTime(eo.getCreateTime());
        po.setUpdateTime(eo.getUpdateTime());
        return po;
    }

    /**
     * PO转EO
     */
    private AiConfigEo poToEo(AiConfigPo po) {
        if (po == null) {
            return null;
        }
        return AiConfigEo.builder()
                .pkId(StringId.of(po.getPkId()))
                .menuId(po.getMenuId() != null ? StringId.of(po.getMenuId()) : null)
                .menuName(po.getMenuName())
                .menuCode(po.getMenuCode())
                .ollamaModelId(po.getOllamaModelId())
                .comfyUiUrl(po.getComfyUiUrl())
                .ollamaUrl(po.getOllamaUrl())
                .comfyFileId(po.getComfyFileId())
                .backgroundImage(po.getBackgroundImage())
                .contextSize(po.getContextSize())
                .createTime(po.getCreateTime())
                .updateTime(po.getUpdateTime())
                .build();
    }
}
