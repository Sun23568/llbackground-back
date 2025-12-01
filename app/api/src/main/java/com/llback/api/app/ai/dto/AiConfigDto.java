package com.llback.api.app.ai.dto;

import lombok.Data;

/**
 * AI配置DTO
 */
@Data
public class AiConfigDto {
    /**
     * 主键ID
     */
    private String pkId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单代码
     */
    private String menuCode;

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
}
