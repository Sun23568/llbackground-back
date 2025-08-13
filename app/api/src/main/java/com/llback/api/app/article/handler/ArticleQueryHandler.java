package com.llback.api.app.article.handler;

import com.llback.api.app.article.dto.req.ArticleQuery;
import com.llback.api.app.article.dto.resp.ArticleResp;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.stereotype.Component;

//@PubAcl
@HandlerAcl("123")
@Component
public class ArticleQueryHandler implements Handler<ArticleResp, ArticleQuery> {
    @Override
    public ArticleResp execute(ArticleQuery req) {
        return null;
    }
}
