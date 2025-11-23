package com.llback.api.app.ai.dto.resp;

import com.llback.api.app.ai.dto.AiConfigDto;
import lombok.Data;

import java.util.List;

/**
 * AI配置列表响应
 *
 * @author llback
 */
@Data
public class AiConfigListResp {
    /**
     * AI配置列表
     */
    private List<AiConfigDto> userAccessRespList;
}
