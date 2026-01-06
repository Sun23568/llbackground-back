-- AI角色卡表
CREATE TABLE IF NOT EXISTS `character_card` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `card_name` VARCHAR(100) NOT NULL COMMENT '角色名称',
  `card_description` VARCHAR(500) DEFAULT NULL COMMENT '角色描述',
  `card_content` TEXT NOT NULL COMMENT '角色卡完整内容(JSON格式)',
  `delete_status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '删除状态: 1-正常, 2-已删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_delete_status` (`delete_status`),
  INDEX `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI角色卡表';
