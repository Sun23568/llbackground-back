-- 角色卡表添加角色名称字段
-- 用于替换角色卡中的 {{char}} 占位符
-- 创建时间: 2026-01-10
-- 说明: 添加 character_name 字段，用于AI对话时替换角色名称占位符

ALTER TABLE character_card
ADD COLUMN character_name VARCHAR(100) COMMENT '角色名称(替换{{char}}占位符)' AFTER user_name;
