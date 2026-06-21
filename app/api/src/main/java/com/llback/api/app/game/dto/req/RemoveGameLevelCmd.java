package com.llback.api.app.game.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

/**
 * 删除游戏关卡命令。
 */
@Data
public class RemoveGameLevelCmd implements Command {
    /**
     * 主键ID。
     */
    private String pkId;
}
