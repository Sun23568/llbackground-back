package com.llback.api.api;

import com.llback.api.app.article.dto.req.QueryArticleContentReq;
import com.llback.api.app.article.dto.req.QueryArticleReq;
import com.llback.api.app.article.dto.req.RemoveArticleCmd;
import com.llback.api.app.article.dto.req.UpdateArticleCmd;
import com.llback.frame.rest.RestApi;
import com.llback.frame.rest.RestResult;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/list")
    public RestResult listArticle(int pageNum, int pageRow) {
        return this.execute(QueryArticleReq.of(pageNum, pageRow));
    }

    /**
     * 查询文章内容
     *
     * @author yz.sun
     * @date 2025/8/22
     */
    @PostMapping("/query/content")
    public RestResult queryArticleContent(@RequestBody QueryArticleContentReq req) {
        return this.execute(req);
    }

    /**
     * 修改或新增文章
     */
    @PostMapping("/update")
    public RestResult updateArticle(@RequestBody UpdateArticleCmd cmd) {
        return this.execute(cmd);
    }

    /**
     * 删除文章
     */
    @PostMapping("/delete")
    public RestResult deleteArticle(@RequestBody RemoveArticleCmd cmd){
        return this.execute(cmd);
    }
}
