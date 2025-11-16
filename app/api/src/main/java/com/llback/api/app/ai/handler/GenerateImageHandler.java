package com.llback.api.app.ai.handler;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.context.SaTokenContext;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.context.model.SaResponse;
import cn.dev33.satoken.context.model.SaStorage;
import com.alibaba.fastjson2.JSONObject;
import com.llback.api.app.ai.dto.AiConfigDto;
import com.llback.api.app.ai.dto.req.GenerateImageReq;
import com.llback.api.app.ai.fetch.AiConfigFetch;
import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.core.article.feign.FtpFileFeign;
import com.llback.frame.Handler;
import com.llback.rt.common.util.OkHttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * 生成图片处理器
 */
@Component
public class GenerateImageHandler implements Handler<ResponseBodyEmitter, GenerateImageReq> {
    /**
     * 模型服务地址
     */
    @Value("${comfy-ui.base-url}")
    private String comfyBaseUrl;

    @Lazy
    @Autowired
    private FtpFileFeign feign;

    /**
     * AI模型服务
     */
    @Autowired
    private AiConfigFetch aiConfigFetch;

    @Override
    public ResponseBodyEmitter execute(GenerateImageReq req) {
        // 校验
        AssertUtil.notEmpty(req.getKeyWord(), "关键词不能为空");
        AssertUtil.notEmpty(req.getModelName(), "AI模型ID不可为空");

        // emitter基础设置
        ResponseBodyEmitter emitter = new ResponseBodyEmitter(10 * 60 * 1000L);
        emitter.onTimeout(() -> {
            emitter.complete();
        });

        SaTokenContext saTokenContext = SaManager.getSaTokenContext();
        SaStorage storage = saTokenContext.getStorage();
        SaRequest request = saTokenContext.getRequest();
        SaResponse response = saTokenContext.getResponse();
        // 启动异步任务
        CompletableFuture.runAsync(() -> {
            // 设置当前线程的RequestAttributes
            SaManager.getSaTokenContext().setContext(request, response, storage);
            /**
             * 1: 拉取JSON文件，构造调用模型生成图片，进入循环等待
             * 2: 返回图片
             */
            // 拉取文件，构造模型参数
            String comfyUIParam = buildComfyUIParam(req.getKeyWord(), req.getModelName());
            try {
                // 构造参数成功
                emitter.send("构造参数成功");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 生成图片
            String imageUrl = generateImage(comfyUIParam);
            try {
                // 发送图片
                emitter.send(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            emitter.complete();
        });
        return emitter;
    }

    /**
     * 构造模型参数
     *
     * @param keyWord
     * @param modelName
     */
    private String buildComfyUIParam(String keyWord, String modelName) {
        // 查询ComfyUI模型文件ID
        AiConfigDto aiConfigDto = aiConfigFetch.queryAiConfig(StringId.of(modelName));
        // 拉取文件
        String param = feign.getFileContent(aiConfigDto.getComfyFileId()).getBody();

        // 构造参数
        JSONObject jsonObject = JSONObject.parseObject(param);
        // 随机种子
        JSONObject seedInputs = jsonObject.getJSONObject("prompt").getJSONObject("3").getJSONObject("inputs");
        seedInputs.put("seed", new BigDecimal(new Random().nextInt(1000000000)));
        // 关键词赋值
        JSONObject keyWordInputs = jsonObject.getJSONObject("prompt").getJSONObject("6").getJSONObject("inputs");
        keyWordInputs.put("text", keyWord);
        // 返回
        return jsonObject.toString();
    }

    /**
     * 生成图片
     *
     * @param param
     * @return
     */
    public String generateImage(String param) {
        // 请求prompt
        String promptResp = OkHttpClientUtils.post(comfyBaseUrl + "/prompt", param);
        // 获取prompt_id
        String promptId = JSONObject.parseObject(promptResp).getString("prompt_id");
        // 请求history
        JSONObject historyResp = JSONObject.parseObject("{}");
        while (historyResp.isEmpty()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String historyRespStr = OkHttpClientUtils.get(comfyBaseUrl + "/history/" + promptId);
            historyResp = JSONObject.parseObject(historyRespStr);
        }
        // 获取文件名与类型
        JSONObject image = historyResp.getJSONObject(promptId).getJSONObject("outputs").getJSONObject("9")
                .getJSONArray("images").getJSONObject(0);
        String fileName = image.getString("filename");
        String type = image.getString("type");
        // 返回base64
        return comfyBaseUrl + "/view?filename=" + fileName + "&type=" + type;
    }
}
