package com.llback.api.app.ai.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

/**
 * 新增AI配置命令
 *
 * @author llback
 */
@Data
public class AddAiConfigCmd implements Command {
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
