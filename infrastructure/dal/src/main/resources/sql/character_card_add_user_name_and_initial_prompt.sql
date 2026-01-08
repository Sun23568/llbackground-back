-- ============================================
-- 角色卡表结构升级：添加用户名和初始提示词字段
-- 执行时间: 2026-01-08 22:02:16
-- 用途: 支持角色卡配置功能，存储用户名和AI初始状态
-- ============================================

-- 为 character_card 表添加用户名称字段
ALTER TABLE `character_card`
ADD COLUMN `user_name` VARCHAR(100) DEFAULT NULL COMMENT '用户名称（对应角色卡中的{{user}}占位符）' AFTER `card_name`;

-- 为 character_card 表添加初始提示词字段
ALTER TABLE `character_card`
ADD COLUMN `initial_prompt` TEXT DEFAULT NULL COMMENT '初始提示词（对应ai_config中的initial_character_state，用于AI生成图片的初始人物状态描述）' AFTER `card_content`;

-- 验证字段是否添加成功
SELECT
    COLUMN_NAME AS '字段名',
    COLUMN_TYPE AS '字段类型',
    IS_NULLABLE AS '是否可空',
    COLUMN_COMMENT AS '字段注释'
FROM
    INFORMATION_SCHEMA.COLUMNS
WHERE
    TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'character_card'
    AND COLUMN_NAME IN ('user_name', 'initial_prompt');
