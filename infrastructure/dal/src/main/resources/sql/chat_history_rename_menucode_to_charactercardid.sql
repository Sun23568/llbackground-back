-- 将聊天历史表的 menu_code 字段重命名为 character_card_id
-- 执行时间：2026-01-11
-- 说明：取消AI页面的menuId概念，统一使用角色卡管理

ALTER TABLE chat_history
CHANGE COLUMN menu_code character_card_id VARCHAR(100) COMMENT '角色卡ID（关联使用的角色卡）';
