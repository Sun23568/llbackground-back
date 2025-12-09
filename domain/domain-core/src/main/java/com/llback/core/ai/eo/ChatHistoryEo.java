package com.llback.core.ai.eo;

import com.llback.common.types.UserId;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class ChatHistoryEo implements Serializable {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 会话ID
     */
    private String conversationId;

    /**
     * 用户ID
     */
    private UserId userId;

    /**
     * 使用的AI模型ID
     */
    private String modelId;

    /**
     * 菜单/功能编码
     */
    private String menuCode;

    /**
     * 用户发送的消息
     */
    private String userMessage;

    /**
     * AI返回的响应
     */
    private String aiResponse;

    /**
     * 记录创建时间
     */
    private LocalDateTime createTime;
}
