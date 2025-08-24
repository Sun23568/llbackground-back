package com.llback.api.app.article.handler;

import com.llback.api.app.article.dto.req.QueryArticleContentReq;
import com.llback.api.app.article.dto.resp.ArticleContentResp;
import com.llback.api.app.article.fetch.ArticleFetch;
import com.llback.common.types.StringId;
import com.llback.frame.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 文章内容查询处理器
 *
 * @author yz.sun
 * @date 2025/8/22
 */
@Component
public class QueryArticleContentHandler implements Handler<ArticleContentResp, QueryArticleContentReq> {
    /**
     * 文章仓库
     */
    @Autowired
    private ArticleFetch articleFetch;

    @Override
    public ArticleContentResp execute(QueryArticleContentReq req) {
        return articleFetch.queryArticleContent(StringId.of(req.getArticleId()), req.getDraft());
    }
}
