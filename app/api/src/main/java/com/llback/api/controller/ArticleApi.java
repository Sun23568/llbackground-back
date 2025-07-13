package com.llback.api.controller;

import com.llback.api.dto.article.req.ArticleQuery;
import com.llback.api.dto.article.resp.ArticleResp;
import com.llback.frame.rest.RestApi;
import com.llback.frame.rest.RestResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: llback
 * @description: 文章相关Controller
 * @date: 2017/10/24 16:04
 */
@RestController
@RequestMapping("/article")
public class ArticleApi implements RestApi {
    /**
     * 查询文章列表
     */
    @PostMapping("/list")
    public RestResult<ArticleResp> listArticle(@RequestBody ArticleQuery articleQuery) {
        return this.execute(articleQuery);
    }
}
