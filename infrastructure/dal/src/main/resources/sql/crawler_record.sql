-- 爬取记录表
-- 如果表已存在，先删除再创建
DROP TABLE IF EXISTS `crawler_record`;

CREATE TABLE `crawler_record` (
  `pk_id` VARCHAR(32) NOT NULL COMMENT '主键ID',
  `config_id` VARCHAR(32) NOT NULL COMMENT '配置ID',
  `config_name` VARCHAR(100) COMMENT '配置名称',
  `execute_time` DATETIME NOT NULL COMMENT '执行时间',
  `status` VARCHAR(20) NOT NULL COMMENT '执行状态',
  `result_data` LONGTEXT COMMENT '爬取结果数据',
  `error_message` TEXT COMMENT '错误信息',
  `duration` BIGINT COMMENT '执行耗时(毫秒)',
  PRIMARY KEY (`pk_id`),
  INDEX `idx_config_id` (`config_id`),
  INDEX `idx_execute_time` (`execute_time` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='爬取记录表';
