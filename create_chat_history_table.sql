CREATE TABLE chat_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    conversation_id VARCHAR(255) NOT NULL COMMENT '会话ID，用于串联单次会话的所有消息',
    user_id VARCHAR(32) NOT NULL COMMENT '用户ID',
    model_id VARCHAR(255) COMMENT '使用的AI模型ID',
    menu_code VARCHAR(255) COMMENT '菜单/功能编码',
    user_message TEXT NOT NULL COMMENT '用户发送的消息',
    ai_response TEXT NOT NULL COMMENT 'AI返回的响应',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间'
);
-- Note: The user_id column type has been changed from BIGINT to VARCHAR(32) to align with the UserId value object which is string-based.