-- 爬虫配置表
-- 如果表已存在，先删除再创建
DROP TABLE IF EXISTS `crawler_config`;

CREATE TABLE `crawler_config` (
  `pk_id` VARCHAR(32) NOT NULL COMMENT '主键ID',
  `config_name` VARCHAR(100) NOT NULL COMMENT '配置名称',
  `target_url` VARCHAR(500) NOT NULL COMMENT '目标URL',
  `request_method` VARCHAR(10) NOT NULL DEFAULT 'GET' COMMENT '请求方法',
  `request_headers` TEXT COMMENT '请求头(JSON格式)',
  `request_body` TEXT COMMENT '请求体',
  `pre_processor` TEXT COMMENT '前置处理器配置(JSON格式)',
  `post_processor` TEXT COMMENT '后置处理器配置(JSON格式)',
  `cron_expression` VARCHAR(100) COMMENT 'Cron表达式',
  `enabled` TINYINT(1) DEFAULT 1 COMMENT '是否启用',
  `description` VARCHAR(500) COMMENT '描述信息',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='爬虫配置表';
