package com.llback.api.api;

import com.llback.api.app.ai.dto.req.GenerateImageReq;
import com.llback.api.app.ai.dto.req.ModelChat;
import com.llback.frame.rest.RestApi;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.servlet.http.HttpServletResponse;

/**
 * AI功能接口
 *
 * @author yz.sun
 * @date 2025/7/14
 */
@RestController
@RequestMapping("/ai")
public class AiApi implements RestApi {
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
    public ResponseBodyEmitter generateImage(@RequestBody GenerateImageReq generateImageReq, HttpServletResponse response) {
        return (ResponseBodyEmitter)this.execute(generateImageReq).getData();
    }
}
