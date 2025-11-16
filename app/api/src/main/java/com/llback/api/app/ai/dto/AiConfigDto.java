package com.llback.api.app.ai.dto;

import lombok.Data;

/**
 * AI模型配置
 */
@Data
public class AiConfigDto {
    /**
     * AI菜单ID
     */
    private String aiMenuId;

    /**
     * 背景图片ID
     */
    private String backgroundFileId;

    /**
     * 上下文大小
     */
    private String contextSize;

    /**
     * ComfyUI模型文件ID
     */
    private String comfyFileId;
}
