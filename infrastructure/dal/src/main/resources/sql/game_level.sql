CREATE TABLE IF NOT EXISTS `game_level` (
  `pk_id` VARCHAR(32) NOT NULL COMMENT '主键ID',
  `level_no` INT NOT NULL COMMENT '关卡编号',
  `level_name` VARCHAR(100) NOT NULL COMMENT '关卡名称',
  `level_data` LONGTEXT NOT NULL COMMENT '关卡JSON数据',
  `enabled` TINYINT(1) DEFAULT 1 COMMENT '是否发布',
  `sort_no` INT DEFAULT 0 COMMENT '排序号',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`pk_id`),
  KEY `idx_game_level_enabled_sort` (`enabled`, `sort_no`, `level_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='游戏关卡表';
