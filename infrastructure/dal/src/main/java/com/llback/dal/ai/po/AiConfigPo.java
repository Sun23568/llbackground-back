package com.llback.dal.ai.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * AI配置PO
 */
@Data
public class AiConfigPo {
    /**
     * 主键ID
     */
    private String pkId;

    /**
     * 菜单代码（存储在表中）
     */
    private String menuCode;

    /**
     * 菜单名称（不存储在表中，通过menu_code关联SYS_MENU表查询获取，仅用于展示）
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
