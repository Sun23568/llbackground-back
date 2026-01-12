package com.llback.api.app.ai.dto.req;

import com.llback.frame.dto.Query;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 查询角色卡列表请求
 *
 * @author HaleyAI
 * @date 2026/1/6
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryCharacterCardListReq implements Query {
    /**
     * 是否显示NSFW内容（默认false，仅显示适合工作场景的内容）
     */
    private Boolean showNsfw;

    /**
     * 空实例（默认不显示NSFW）
     */
    public static QueryCharacterCardListReq EMPTY = new QueryCharacterCardListReq(false);

    /**
     * 创建实例
     *
     * @param showNsfw 是否显示NSFW内容
     * @return 查询请求对象
     */
    public static QueryCharacterCardListReq of(Boolean showNsfw) {
        return QueryCharacterCardListReq.builder()
                .showNsfw(showNsfw != null && showNsfw)
                .build();
    }
}
