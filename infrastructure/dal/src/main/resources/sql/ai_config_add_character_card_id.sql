-- AI配置表添加角色卡ID字段
-- 执行日期：2026-01-07

ALTER TABLE `ai_config`
ADD COLUMN `character_card_id` VARCHAR(32) DEFAULT NULL COMMENT '角色卡ID' AFTER `initial_character_state`;

-- 添加索引以提高查询性能（可选）
ALTER TABLE `ai_config`
ADD INDEX `idx_character_card_id` (`character_card_id`);
