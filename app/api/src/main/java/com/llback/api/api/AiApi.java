package com.llback.api.api;

import com.llback.api.app.ai.dto.req.AiConfigReq;
import com.llback.api.app.ai.dto.req.GenerateImageReq;
import com.llback.api.app.ai.dto.req.ModelChat;
import com.llback.api.app.ai.dto.req.UploadBackgroundReq;
import com.llback.frame.rest.RestApi;
import com.llback.frame.rest.RestResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.servlet.http.HttpServletResponse;

/**
 * 鉴权接口
 *
 * @author yz.sun
 * @date 2025/7/14
 */
@RestController
@RequestMapping("/ai")
public class AiApi implements RestApi {
    /**
     * 根据AI菜单ID获取背景图片
     */
    @RequestMapping("/background")
    public RestResult getAiBackground(@RequestParam("aiMenuId") String aiMenuId) {
        return this.execute(AiConfigReq.of(aiMenuId));
    }

    /**
     * 上传背景图片
     */
    @RequestMapping("/upload/background")
    public RestResult getBackground(@RequestParam("file") MultipartFile file, @RequestParam("aiMenuId") String aiMenuId) {
        return this.execute(UploadBackgroundReq.of(file, aiMenuId));
    }

    /**
     * 模型聊天
     */
    @RequestMapping("/chat")
    public ResponseBodyEmitter modelChat(@RequestBody ModelChat modelChat, HttpServletResponse response) {
        return (ResponseBodyEmitter)this.execute(modelChat.setServlet(response)).getData();
    }

    /**
     * 文生图
     */
    @RequestMapping("/generate-image")
    public ResponseBodyEmitter modelChat(@RequestBody GenerateImageReq generateImageReq, HttpServletResponse response) {
        return (ResponseBodyEmitter)this.execute(generateImageReq).getData();
    }
}
