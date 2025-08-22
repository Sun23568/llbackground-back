package com.llback.api.app.article.handler;

import com.github.pagehelper.PageInfo;
import com.llback.api.app.article.dto.ArticleDto;
import com.llback.api.app.article.dto.req.QueryArticleContentReq;
import com.llback.api.app.article.dto.req.QueryArticleReq;
import com.llback.api.app.article.dto.resp.ArticleContentResp;
import com.llback.api.app.article.fetch.ArticleFetch;
import com.llback.api.util.DtoEoAssemblerUtil;
import com.llback.common.types.StringId;
import com.llback.core.article.eo.ArticleEo;
import com.llback.core.article.repository.ArticleRepository;
import com.llback.frame.Handler;
import com.llback.frame.dto.PageResult;
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
        return articleFetch.queryArticleContent(StringId.of(req.getArticleId()), req.getCraft());
    }
}
