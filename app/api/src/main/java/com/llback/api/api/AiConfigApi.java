package com.llback.api.api;


import com.llback.api.app.ai.dto.req.AddAiConfigCmd;
import com.llback.api.app.ai.dto.req.AiConfigListReq;
import com.llback.api.app.ai.dto.req.UpdateAiConfigCmd;
import com.llback.frame.rest.RestApi;
import com.llback.frame.rest.RestResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AI配置接口
 */
@RestController
@RequestMapping("/ai/config")
public class AiConfigApi implements RestApi {
    /**
     * 获取AI配置列表
     */
    @PostMapping("/list")
    public RestResult getAiConfigList() {
        return this.execute(AiConfigListReq.EMPTY);
    }

    /**
     * 新增AI配置
     */
    @PostMapping("/add")
    public RestResult addAiConfig(@RequestBody AddAiConfigCmd cmd) {
        return this.execute(cmd);
    }

    /**
     * 更新AI配置
     */
    @PostMapping("/update")
    public RestResult updateAiConfig(@RequestBody UpdateAiConfigCmd cmd) {
        return this.execute(cmd);
    }
}
