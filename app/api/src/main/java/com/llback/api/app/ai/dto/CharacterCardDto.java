package com.llback.api.app.ai.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色卡DTO
 *
 * @author HaleyAI
 * @date 2026/1/6
 */
@Data
public class CharacterCardDto {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 角色卡名称
     */
    private String cardName;

    /**
     * 用户名称（对应角色卡中的{{user}}占位符）
     */
    private String userName;

    /**
     * 角色卡描述
     */
    private String cardDescription;

    /**
     * 角色卡完整内容(JSON格式)
     */
    private String cardContent;

    /**
     * 初始提示词（用于AI生成图片的初始人物状态描述）
     */
    private String initialPrompt;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
