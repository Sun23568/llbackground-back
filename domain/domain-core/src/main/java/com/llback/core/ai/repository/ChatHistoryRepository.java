package com.llback.core.ai.repository;

import com.llback.core.ai.eo.ChatHistoryEo;

public interface ChatHistoryRepository {

    /**
     * 保存聊天记录
     *
     * @param chatHistoryEo 聊天记录实体
     */
    void save(ChatHistoryEo chatHistoryEo);
}
