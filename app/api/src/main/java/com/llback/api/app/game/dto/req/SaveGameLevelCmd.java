package com.llback.api.app.game.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

/**
 * 保存游戏关卡命令。
 */
@Data
public class SaveGameLevelCmd implements Command {
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
}
