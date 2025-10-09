package com.llback.dal.ai.repository;

import com.llback.common.types.StringId;
import com.llback.core.ai.repository.AiRepository;
import com.llback.dal.ai.dao.AiDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AI模块仓储实现类
 *
 * @author yz.sun
 * @date 2025/10/6
 */
@Component
public class AiRepositoryImpl implements AiRepository {
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
}
