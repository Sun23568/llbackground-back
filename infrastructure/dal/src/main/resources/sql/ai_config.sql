-- AI配置表
-- 如果表已存在，先删除再创建
DROP TABLE IF EXISTS `ai_config`;

CREATE TABLE `ai_config` (
  `pk_id` VARCHAR(32) NOT NULL COMMENT '主键ID',
  `menu_code` VARCHAR(100) NOT NULL COMMENT '菜单代码，关联SYS_MENU表的MENU_CODE',
  `ollama_model_id` VARCHAR(100) DEFAULT NULL COMMENT 'Ollama模型ID',
  `comfy_ui_url` VARCHAR(255) DEFAULT NULL COMMENT 'ComfyUI地址',
  `ollama_url` VARCHAR(255) DEFAULT NULL COMMENT 'Ollama地址',
  `comfy_file_id` VARCHAR(32) DEFAULT NULL COMMENT 'ComfyUI参数文件ID',
  `background_image` VARCHAR(255) DEFAULT NULL COMMENT '背景图片',
  `context_size` INT DEFAULT 1024 COMMENT '上下文大小',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`pk_id`),
  UNIQUE KEY `uk_menu_code` (`menu_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI配置表';
