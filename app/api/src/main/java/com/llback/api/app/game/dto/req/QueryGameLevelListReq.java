package com.llback.api.app.game.dto.req;

import com.llback.frame.dto.Query;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 查询游戏关卡列表请求。
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryGameLevelListReq implements Query {
    /**
     * 页码。
     */
    private Integer pageNum = 1;

    /**
     * 每页条数。
     */
    private Integer pageRow = 10;

    /**
     * 创建请求。
     */
    public static QueryGameLevelListReq of(Integer pageNum, Integer pageRow) {
        return QueryGameLevelListReq.builder()
                .pageNum(pageNum == null || pageNum < 1 ? 1 : pageNum)
                .pageRow(pageRow == null || pageRow < 1 ? 10 : pageRow)
                .build();
    }
}
