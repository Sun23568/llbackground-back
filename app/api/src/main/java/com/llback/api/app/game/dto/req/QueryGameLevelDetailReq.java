package com.llback.api.app.game.dto.req;

import com.llback.frame.dto.Query;
import lombok.Data;

/**
 * 查询游戏关卡详情请求。
 */
@Data
public class QueryGameLevelDetailReq implements Query {
    /**
     * 主键ID。
     */
    private String pkId;
}
