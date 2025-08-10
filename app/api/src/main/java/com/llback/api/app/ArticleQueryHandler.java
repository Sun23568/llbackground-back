package com.llback.api.app;

import com.llback.api.dto.article.req.ArticleQuery;
import com.llback.api.dto.article.resp.ArticleResp;
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
