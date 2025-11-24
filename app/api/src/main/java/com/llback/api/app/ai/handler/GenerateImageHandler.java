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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
public class GenerateImageHandler implements Handler<ResponseBodyEmitter, GenerateImageReq> {
    /**
     * 轮询最大重试次数
     */
    private static final int MAX_RETRY_COUNT = 120;

    /**
     * 轮询间隔时间（毫秒）
     */
    private static final long POLL_INTERVAL_MS = 500;
    /**
     * 模型服务地址（作为默认值）
     */
    @Value("${comfy-ui.base-url}")
    private String comfyBaseUrl;

    @Lazy
    @Autowired
    private FtpFileFeign feign;

    /**
     * AI配置查询服务
     */
    @Autowired
    private AiConfigFetch aiConfigFetch;

    @Override
    public ResponseBodyEmitter execute(GenerateImageReq req) {
        // 校验
        AssertUtil.notEmpty(req.getKeyWord(), "关键词不能为空");
        AssertUtil.notEmpty(req.getAiMenuCode(), "AI菜单代码不能为空");

        // 查询AI配置
        AiConfigDto aiConfig = aiConfigFetch.queryAiConfig(StringId.of(req.getAiMenuCode()));
        AssertUtil.notNull(aiConfig, "AI配置不存在");

        // 获取ComfyUI服务地址，如果配置中没有则使用默认值
        String comfyUiUrl = cn.hutool.core.util.StrUtil.isNotBlank(aiConfig.getComfyUiUrl())
                ? aiConfig.getComfyUiUrl()
                : comfyBaseUrl;

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
            String comfyUIParam = buildComfyUIParam(req.getKeyWord(), aiConfig);
            try {
                // 构造参数成功
                emitter.send("构造参数成功");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 生成图片
            String imageUrl = generateImage(comfyUIParam, comfyUiUrl);
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
     * @param keyWord 关键词
     * @param aiConfig AI配置
     */
    private String buildComfyUIParam(String keyWord, AiConfigDto aiConfig) {
        // 拉取ComfyUI参数文件
        String param = feign.getFileContent(aiConfig.getComfyFileId()).getBody();

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
     * @param param ComfyUI参数
     * @param comfyUiUrl ComfyUI服务地址（从AI配置表读取）
     * @return 图片URL
     */
    public String generateImage(String param, String comfyUiUrl) {
        try {
            // 请求prompt
            log.info("开始调用ComfyUI生成图片, URL: {}", comfyUiUrl);
            String promptResp = OkHttpClientUtils.post(comfyUiUrl + "/prompt", param);
            // 获取prompt_id
            String promptId = JSONObject.parseObject(promptResp).getString("prompt_id");
            log.info("获取到prompt_id: {}", promptId);

            // 轮询获取生成结果，添加超时机制
            JSONObject historyResp = null;
            int retryCount = 0;
            while (retryCount < MAX_RETRY_COUNT) {
                try {
                    Thread.sleep(POLL_INTERVAL_MS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.warn("线程被中断", e);
                    throw new RuntimeException("图片生成被中断", e);
                }

                String historyRespStr = OkHttpClientUtils.get(comfyUiUrl + "/history/" + promptId);
                JSONObject tempResp = JSONObject.parseObject(historyRespStr);

                if (tempResp != null && !tempResp.isEmpty()) {
                    historyResp = tempResp;
                    log.info("成功获取到图片生成结果，重试次数: {}", retryCount);
                    break;
                }

                retryCount++;
                if (retryCount % 10 == 0) {
                    log.info("等待图片生成中... 已重试{}次", retryCount);
                }
            }

            if (historyResp == null || historyResp.isEmpty()) {
                log.error("图片生成超时，超过最大重试次数: {}", MAX_RETRY_COUNT);
                throw new RuntimeException("图片生成超时，请稍后重试");
            }

            // 获取文件名与类型
            JSONObject image = historyResp.getJSONObject(promptId)
                    .getJSONObject("outputs")
                    .getJSONObject("9")
                    .getJSONArray("images")
                    .getJSONObject(0);
            String fileName = image.getString("filename");
            String type = image.getString("type");

            // 返回图片URL
            String imageUrl = comfyUiUrl + "/view?filename=" + fileName + "&type=" + type;
            log.info("图片生成成功, URL: {}", imageUrl);
            return imageUrl;
        } catch (Exception e) {
            log.error("图片生成失败", e);
            throw new RuntimeException("图片生成失败: " + e.getMessage(), e);
        }
    }
}
