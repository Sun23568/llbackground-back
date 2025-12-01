package com.llback.core.ai.eo;

import com.llback.common.types.MenuCode;
import com.llback.common.types.StringId;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * AI配置实体对象
 *
 * @author llback
 */
@Getter
@Builder
public class AiConfigEo {
    /**
     * 主键ID
     */
    private StringId pkId;

    /**
     * 菜单代码
     */
    private MenuCode menuCode;

    /**
     * 菜单名称（从菜单表关联查询获取，用于展示）
     */
    private String menuName;

    /**
     * Ollama模型ID
     */
    private String ollamaModelId;

    /**
     * ComfyUI地址
     */
    private String comfyUiUrl;

    /**
     * Ollama地址
     */
    private String ollamaUrl;

    /**
     * ComfyUI参数文件ID
     */
    private String comfyFileId;

    /**
     * 背景图片
     */
    private String backgroundImage;

    /**
     * 上下文大小
     */
    private Integer contextSize;

    /**
     * 初始人物状态（JSON格式）
     */
    private String initialCharacterState;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
