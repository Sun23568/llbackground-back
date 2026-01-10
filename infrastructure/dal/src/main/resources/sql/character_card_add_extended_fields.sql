-- 角色卡表扩展字段迁移脚本
-- 添加 first_mes, avatar, mes_example, scenario 字段
-- 创建时间: 2026-01-10 20:41:37
-- 说明: 扩展角色卡配置功能，支持保存开场白、头像、对话示例和场景描述

ALTER TABLE character_card
ADD COLUMN first_mes TEXT COMMENT '开场白' AFTER initial_prompt,
ADD COLUMN avatar VARCHAR(500) COMMENT '头像URL' AFTER first_mes,
ADD COLUMN mes_example TEXT COMMENT '对话示例' AFTER avatar,
ADD COLUMN scenario VARCHAR(200) COMMENT '场景描述' AFTER mes_example;
