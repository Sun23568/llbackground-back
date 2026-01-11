package com.llback.api.app.ai.handler;

import cn.hutool.core.collection.CollectionUtil;
import com.llback.api.app.ai.dto.AiConfigDto;
import com.llback.api.app.ai.dto.req.ModelChat;
import com.llback.api.app.ai.fetch.AiConfigFetch;
import com.llback.common.types.StringId;
import com.llback.common.types.UserId;
import com.llback.common.util.AssertUtil;
import com.llback.core.ai.eo.CharacterCardEo;
import com.llback.core.ai.eo.ChatHistoryEo;
import com.llback.core.ai.repository.CharacterCardRepository;
import com.llback.core.ai.repository.ChatHistoryRepository;
import com.llback.frame.Handler;
import com.llback.frame.context.ReqContext;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.output.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * 模型聊天处理器
 */
@Slf4j
@Component
public class ModelChatHandler implements Handler<ResponseBodyEmitter, ModelChat> {
    /**
     * AI配置查询服务
     */
    @Autowired
    private AiConfigFetch aiConfigFetch;

    @Autowired
    private ChatHistoryRepository chatHistoryRepository;

    /**
     * 角色卡仓储
     */
    @Autowired
    private CharacterCardRepository characterCardRepository;

    /**
     * 模型聊天
     */
    @Override
    public ResponseBodyEmitter execute(ModelChat req) {
        AssertUtil.notEmpty(req.getModel(), "模型不能为空");

        // 查询AI配置（已包含从配置文件读取的默认地址）
        AiConfigDto aiConfig = aiConfigFetch.queryAiConfig();
        AssertUtil.notNull(aiConfig, "AI配置不存在");
        AssertUtil.notEmpty(aiConfig.getOllamaUrl(), "Ollama服务地址未配置");

        // 获取Ollama服务地址（已由queryAiConfig方法填充）
        String ollamaUrl = aiConfig.getOllamaUrl();

        // 获取上下文大小，如果配置中没有则使用默认值
        Integer contextSize = aiConfig.getContextSize() != null ? aiConfig.getContextSize() : 10;

        // 查询角色卡描述（如果提供了角色卡ID）
        final CharacterCardEo characterCardEo;
        if (StringUtils.isNotBlank(req.getCharacterCardId())) {
            CharacterCardEo card = characterCardRepository.findById(StringId.of(req.getCharacterCardId()));
            AssertUtil.notNull(card, "角色卡不存在");
            characterCardEo = card;
        } else {
            characterCardEo = null;
        }

        // 返回emitter
        ResponseBodyEmitter emitter = new ResponseBodyEmitter(10 * 60 * 1000L);
        // response content-type
        req.getResponse().setContentType("application/x-ndjson");
        emitter.onTimeout(() -> {
            emitter.complete();
        });

        // 获取用户ID和生成会话ID
        UserId userId = ReqContext.getCurrent().getUserSession().getUserId();
        String conversationId = UUID.randomUUID().toString();

        // 启动异步任务
        CompletableFuture.runAsync(() -> {
            try {
                OllamaStreamingChatModel model = OllamaStreamingChatModel.builder()
                        .baseUrl(ollamaUrl)
                        .timeout(Duration.ofDays(1))
                        .modelName(req.getModel())
                        .build();
                // 构造上下文，使用配置的上下文大小
                List<ChatMessage> chatMessages = buildChatMessages(req.getContext(), req.getMessage(), contextSize, characterCardEo);
                model.generate(chatMessages, new StreamingResponseHandler<AiMessage>() {
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
                    public void onComplete(Response<AiMessage> response) {
                        try {
                            ChatHistoryEo history = ChatHistoryEo.builder()
                                    .conversationId(conversationId)
                                    .userId(userId)
                                    .modelId(req.getModel())
                                    .characterCardId(req.getCharacterCardId())
                                    .userMessage(req.getMessage())
                                    .aiResponse(response.content().text()) // Get full response
                                    .createTime(LocalDateTime.now())
                                    .build();
                            chatHistoryRepository.save(history);
                        } catch (Exception e) {
                            // Log the error, but don't fail the emitter for a history save failure
                            log.error("Failed to save chat history: " + e.getMessage());
                        } finally {
                            emitter.complete();
                        }
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
     * @param context 上下文消息列表
     * @param message 当前消息
     * @param contextSize 上下文大小（从AI配置表读取）
     * @param characterCardEo 系统消息（角色卡描述，可选）
     */
    private List<ChatMessage> buildChatMessages(List<String> context, String message, Integer contextSize, CharacterCardEo characterCardEo) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        // 添加用户消息
        chatMessages.add(UserMessage.from(message));
        // 标识 true 表示用户消息，false 表示模型消息
        boolean flag = true;
        // 添加上下文消息，限制上下文数量
        if (CollectionUtil.isNotEmpty(context)) {
            // 计算实际使用的上下文数量（不超过配置的contextSize）
            int actualContextSize = Math.min(context.size(), contextSize);
            for (int index = context.size() - 1; index >= context.size() - actualContextSize; index--) {
                String msg = context.get(index);
                if (!flag) {
                    chatMessages.add(0, UserMessage.from(msg));
                } else {
                    chatMessages.add(0, AiMessage.from(msg));
                }
                flag = !flag;
            }
        }

        // 添加系统消息（不计入上下文数量限制）
        if (characterCardEo != null) {
            chatMessages.add(0, SystemMessage.from(characterCardEo.getCardDescription()));
            chatMessages.add(0, SystemMessage.from(String.format("{user}=%s", characterCardEo.getUserName())));
            chatMessages.add(0, SystemMessage.from(String.format("{char}=%s", characterCardEo.getCharacterName())));
            chatMessages.add(0, SystemMessage.from(String.format("场景=%s", characterCardEo.getScenario())));
        }
        return chatMessages;
    }
}
