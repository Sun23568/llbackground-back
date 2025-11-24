package com.llback.api.app.ai.dto.req;

import com.llback.frame.dto.Query;
import lombok.Data;

@Data
public class GenerateImageReq implements Query {
    /**
     * 关键词
     */
    private String keyWord;

    /**
     * AI菜单代码（menu_code，用于获取AI配置）
     */
    private String aiMenuCode;
}
