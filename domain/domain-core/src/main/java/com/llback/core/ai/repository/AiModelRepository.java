package com.llback.core.ai.repository;

import com.llback.common.types.StringId;

public interface AiModelRepository {
    /**
     * 更新AI菜单背景图片
     *
     * @author yz.sun
     * @date 2025/10/6
     */
    void updateBackground(StringId aiMenuId, StringId backgroundId);
}
