package com.llback.core.game.eo;

import com.llback.common.types.StringId;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 游戏关卡领域实体。
 */
@Getter
@Builder
public class GameLevelEo {
    /**
     * 主键ID。
     */
    private StringId pkId;

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
