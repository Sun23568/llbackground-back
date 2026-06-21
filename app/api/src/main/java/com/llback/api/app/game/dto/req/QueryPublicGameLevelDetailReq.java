package com.llback.api.app.game.dto.req;

import com.llback.frame.dto.Query;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 查询公开游戏关卡详情请求。
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryPublicGameLevelDetailReq implements Query {
    /**
     * 主键ID。
     */
    private String pkId;

    /**
     * 创建请求。
     */
    public static QueryPublicGameLevelDetailReq of(String pkId) {
        return QueryPublicGameLevelDetailReq.builder()
                .pkId(pkId)
                .build();
    }
}
