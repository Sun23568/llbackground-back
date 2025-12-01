-- AI配置表 - 添加初始人物状态字段
-- 此脚本用于在现有表上添加新列，不会删除现有数据

-- 添加初始人物状态列
ALTER TABLE `ai_config`
ADD COLUMN `initial_character_state` TEXT DEFAULT NULL COMMENT '初始人物状态（JSON格式：发色、面部、上身、下身、鞋子）'
AFTER `context_size`;

-- 验证列是否添加成功
SELECT
    COLUMN_NAME,
    COLUMN_TYPE,
    IS_NULLABLE,
    COLUMN_COMMENT
FROM
    INFORMATION_SCHEMA.COLUMNS
WHERE
    TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'ai_config'
    AND COLUMN_NAME = 'initial_character_state';
