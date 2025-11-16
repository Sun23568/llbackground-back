package com.llback.api.app.ai.handler;

import com.llback.api.app.ai.dto.req.AiConfigListReq;
import com.llback.api.app.ai.fetch.AiConfigFetch;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AI菜单配置列表查询处理器
 */
@Component
@HandlerAcl("ai:config:list")
public class QueryAiConfigListHandler implements Handler<Void, AiConfigListReq> {
    /**
     * AI菜单配置查询
     */
    @Autowired
    private AiConfigFetch aiConfigFetch;

    @Override
    public Void execute(AiConfigListReq req) {
        // 查询AI菜单列表
        aiConfigFetch.queryAllAiConfigList();
        return null;
    }
}
