package com.llback.api.api;


import com.llback.api.app.ai.dto.req.AddAiConfigCmd;
import com.llback.api.app.ai.dto.req.AiConfigListReq;
import com.llback.api.app.ai.dto.req.AiConfigReq;
import com.llback.api.app.ai.dto.req.UpdateAiConfigCmd;
import com.llback.api.app.ai.dto.req.UploadBackgroundReq;
import com.llback.frame.rest.RestApi;
import com.llback.frame.rest.RestResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 获取AI菜单背景图片配置
     */
    @GetMapping("/background-image")
    public RestResult getBackgroundImage(@RequestParam("aiMenuCode") String aiMenuCode) {
        return this.execute(AiConfigReq.of(aiMenuCode));
    }

    /**
     * 上传AI菜单背景图片
     */
    @PostMapping("/background-image/upload")
    public RestResult uploadBackgroundImage(@RequestParam("file") MultipartFile file,
                                            @RequestParam("aiMenuCode") String aiMenuCode) {
        return this.execute(UploadBackgroundReq.of(file, aiMenuCode));
    }
}
