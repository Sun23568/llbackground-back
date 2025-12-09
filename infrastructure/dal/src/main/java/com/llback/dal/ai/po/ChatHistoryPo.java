package com.llback.dal.ai.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatHistoryPo {

    private Long id;
    private String conversationId;
    private String userId;
    private String modelId;
    private String menuCode;
    private String userMessage;
    private String aiResponse;
    private LocalDateTime createTime;
}
