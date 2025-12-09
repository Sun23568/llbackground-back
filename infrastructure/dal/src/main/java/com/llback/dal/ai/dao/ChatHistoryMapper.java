package com.llback.dal.ai.dao;

import com.llback.dal.ai.po.ChatHistoryPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatHistoryMapper {

    /**
     * 新增聊天记录
     *
     * @param chatHistoryPo 聊天记录PO
     */
    void insert(ChatHistoryPo chatHistoryPo);
}
