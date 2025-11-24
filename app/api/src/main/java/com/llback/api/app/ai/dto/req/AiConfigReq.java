package com.llback.api.app.ai.dto.req;

import com.llback.frame.dto.Query;
import lombok.Data;

@Data
public class AiConfigReq implements Query {
    /**
     * AI菜单代码（menu_code）
     */
    private String aiMenuCode;

    private AiConfigReq(String aiMenuCode) {
        this.aiMenuCode = aiMenuCode;
    }

    public static AiConfigReq of(String aiMenuCode) {
        return new AiConfigReq(aiMenuCode);
    }
}
