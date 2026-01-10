package com.llback.dal.ai.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色卡持久化对象
 *
 * @author HaleyAI
 * @date 2026/1/6
 */
@Data
public class CharacterCardPo {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 角色卡名称
     */
    private String cardName;

    /**
     * 用户名称（对应角色卡中的{{user}}占位符）
     */
    private String userName;

    /**
     * 角色名称（对应角色卡中的{{char}}占位符）
     */
    private String characterName;

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
     * 开场白
     */
    private String firstMes;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 对话示例
     */
    private String mesExample;

    /**
     * 场景描述
     */
    private String scenario;

    /**
     * 删除状态: 1-正常, 2-已删除
     */
    private Integer deleteStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
