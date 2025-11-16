package com.llback.api.app.ai.handler;

import cn.hutool.core.collection.CollectionUtil;
import com.llback.api.app.ai.dto.req.ModelChat;
import com.llback.common.util.AssertUtil;
import com.llback.frame.Handler;
import com.llback.frame.context.ReqContext;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.output.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 模型聊天处理器
 */
@Component
public class ModelChatHandler implements Handler<ResponseBodyEmitter, ModelChat> {
    /**
     * 模型服务地址
     */
    @Value("${ollama.base-url}")
    private String modelBaseUrl;

    /**
     * 模型聊天
     */
    @Override
    public ResponseBodyEmitter execute(ModelChat req) {
        AssertUtil.notEmpty(req.getModel(), "模型不能为空");
        // 模型权限校验
        AssertUtil.assertTrue(ReqContext.getCurrent().getUserSession().hasPerm("ai:model:" + req.getModel()), "无权限访问该模型");
        // 返回emitter
        ResponseBodyEmitter emitter = new ResponseBodyEmitter(10 * 60 * 1000L);
        // response content-type
        req.getResponse().setContentType("application/x-ndjson");
        emitter.onTimeout(() -> {
            emitter.complete();
        });

        // 启动异步任务
        CompletableFuture.runAsync(() -> {
            try {
                OllamaStreamingChatModel model = OllamaStreamingChatModel.builder()
                        .baseUrl(modelBaseUrl)
                        .timeout(Duration.ofDays(1))
                        .modelName(req.getModel())
                        .build();
                // 构造上下文
                List<ChatMessage> chatMessages = buildChatMessages(req.getContext(), req.getMessage());
                model.generate(chatMessages, new StreamingResponseHandler() {
                    @Override
                    public void onNext(String chatResp) {
                        try {
                            emitter.send(chatResp);
                        } catch (IOException e) {
                            // 客户端断开连接，Spring会自动处理
                            System.out.println("客户端断开连接: " + e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        try {
                            emitter.send("【对话模型异常，请联系孙老六】");
                        } catch (IOException e) {
                            // 忽略
                        } finally {
                            emitter.completeWithError(throwable);
                        }
                    }

                    @Override
                    public void onComplete(Response response) {
                        emitter.complete();
                    }
                });
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }

    /**
     * 构造上下文
     *
     * @param context
     * @param message
     */
    private List<ChatMessage> buildChatMessages(List<String> context, String message) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        // 添加用户消息
        chatMessages.add(UserMessage.from(message));
        // 标识 true 表示用户消息，false 表示模型消息
        boolean flag = true;
        // 添加上下文消息
        if (CollectionUtil.isNotEmpty(context)) {
            for (int index = context.size() - 1; index >= 0; index--) {
                String msg = context.get(index);
                if (!flag) {
                    chatMessages.add(0, UserMessage.from(msg));
                } else {
                    chatMessages.add(0, AiMessage.from(msg));
                }
                flag = !flag;
            }
        }
        return chatMessages;
    }
}
