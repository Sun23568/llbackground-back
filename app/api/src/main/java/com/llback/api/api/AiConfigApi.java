package com.llback.api.api;


import com.llback.api.app.ai.dto.req.AiConfigListReq;
import com.llback.frame.rest.RestApi;
import com.llback.frame.rest.RestResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AI菜单配置接口
 */
@RestController
@RequestMapping("/ai/config")
public class AiConfigApi implements RestApi {
    /**
     * 获取AI菜单配置列表
     */
    @PostMapping("/list")
    public RestResult getAiConfigList() {
        return this.execute(AiConfigListReq.EMPTY);
    }
}
