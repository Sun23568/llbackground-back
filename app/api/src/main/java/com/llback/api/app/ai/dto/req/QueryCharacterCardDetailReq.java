package com.llback.api.app.ai.dto.req;

import com.llback.frame.dto.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 查询角色卡详情请求
 *
 * @author HaleyAI
 * @date 2026/1/11
 */
@Getter
@AllArgsConstructor(staticName = "of")
public class QueryCharacterCardDetailReq implements Query {
    /**
     * 角色卡ID
     */
    private final String cardId;
}
