package com.llback.api.app.ai.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

/**
 * 更新角色卡配置命令
 *
 * @author HaleyAI
 * @date 2026/1/8
 */
@Data
public class UpdateCharacterCardConfigCmd implements Command {

    /**
     * 角色卡ID
     */
    private String id;

    /**
     * 角色名称
     */
    private String cardName;

    /**
     * 用户名称（对应角色卡中的{{user}}占位符）
     */
    private String userName;

    /**
     * 初始提示词（用于AI生成图片的初始人物状态描述）
     */
    private String initialPrompt;
}
