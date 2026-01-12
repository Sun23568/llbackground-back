-- 为 character_card 表添加 nsfw 字段
-- nsfw: Not Safe For Work（不适合工作场景观看）
-- 0-否（适合工作场景），1-是（不适合工作场景）
-- 默认值为 0

ALTER TABLE character_card
ADD COLUMN nsfw TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否NSFW: 0-否, 1-是' AFTER scenario;
