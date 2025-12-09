package com.llback.dal.ai.repository;

import com.llback.core.ai.eo.ChatHistoryEo;
import com.llback.core.ai.repository.ChatHistoryRepository;
import com.llback.dal.ai.dao.ChatHistoryMapper;
import com.llback.dal.ai.po.ChatHistoryPo;
import com.llback.rt.common.util.PoAssembleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ChatHistoryRepositoryImpl implements ChatHistoryRepository {

    @Autowired
    private ChatHistoryMapper chatHistoryMapper;

    @Override
    public void save(ChatHistoryEo chatHistoryEo) {
        ChatHistoryPo chatHistoryPo = PoAssembleUtil.eo2Po(chatHistoryEo, ChatHistoryPo.class);
        chatHistoryMapper.insert(chatHistoryPo);
    }
}
