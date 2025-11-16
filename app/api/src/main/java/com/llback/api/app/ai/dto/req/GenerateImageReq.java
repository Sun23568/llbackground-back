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
     * 模型名称
     */
    private String modelName;
}
