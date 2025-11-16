package com.llback.dal.ai.po;

import lombok.Data;

/**
 * AI模型配置
 */
@Data
public class AiConfigPo {
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
     * ComfyUI文件ID
     */
    private String comfyFileId;
}
