package com.llback.api.app.ai.dto.req;

import com.llback.frame.dto.Query;
import lombok.Data;

@Data
public class AiConfigReq implements Query {
    /**
     * AI菜单ID
     */
    private String aiMenuId;

    private AiConfigReq(String aiMenuId) {
        this.aiMenuId = aiMenuId;
    }

    public static AiConfigReq of(String aiMenuId) {
        return new AiConfigReq(aiMenuId);
    }
}
