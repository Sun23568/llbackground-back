package com.llback.dal.game.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 游戏关卡持久化对象。
 */
@Data
public class GameLevelPo {
    /**
     * 主键ID。
     */
    private String pkId;

    /**
     * 关卡编号。
     */
    private Integer levelNo;

    /**
     * 关卡名称。
     */
    private String levelName;

    /**
     * 关卡JSON数据。
     */
    private String levelData;

    /**
     * 是否发布。
     */
    private Boolean enabled;

    /**
     * 排序号。
     */
    private Integer sortNo;

    /**
     * 创建时间。
     */
    private LocalDateTime createTime;

    /**
     * 更新时间。
     */
    private LocalDateTime updateTime;
}
