package com.llback.core.ai.repository;

import com.llback.common.types.StringId;

public interface AiModelRepository {
    /**
     * 更新AI菜单背景图片
     *
     * @param aiMenuCode AI菜单代码
     * @param backgroundImageId 背景图片ID
     * @author yz.sun
     * @date 2025/10/6
     */
    void updateBackgroundImage(StringId aiMenuCode, StringId backgroundImageId);
}
