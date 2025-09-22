package com.llback.api.app.article.handler;

import com.llback.api.app.article.dto.req.QueryArticleContentReq;
import com.llback.api.app.article.dto.resp.ArticleContentResp;
import com.llback.api.app.article.fetch.ArticleFetch;
import com.llback.common.types.StringId;
import com.llback.core.article.service.ArticleService;
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

    /**
     * 文章服务
     */
    @Autowired
    private ArticleService articleService;

    @Override
    public ArticleContentResp execute(QueryArticleContentReq req) {
        ArticleContentResp articleContentResp = articleFetch.queryArticleContent(StringId.of(req.getArticleId()), req.getDraft());
        // 转换文章中图片信息
        articleContentResp.setContent(articleService.handlerHtmlImageToUrl(articleContentResp.getContent()));
        return articleContentResp;
    }
}
