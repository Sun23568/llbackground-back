package com.llback.core.ai.eo;

import com.llback.common.types.SafeText;
import com.llback.common.types.StringId;
import com.llback.common.types.UserId;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 角色卡领域实体对象
 *
 * @author HaleyAI
 * @date 2026/1/6
 */
@Getter
@Builder
public class CharacterCardEo {
    /**
     * 主键ID
     */
    private StringId id;

    /**
     * 用户ID
     */
    private UserId userId;

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

    /**
     * 判断角色卡是否有效（未删除）
     *
     * @return true-有效, false-已删除
     */
    public boolean isValid() {
        return this.deleteStatus != null && this.deleteStatus == 1;
    }
}
