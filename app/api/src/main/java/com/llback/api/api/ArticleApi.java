package com.llback.api.api;

import com.llback.api.app.article.dto.req.*;
import com.llback.frame.rest.RestApi;
import com.llback.frame.rest.RestResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

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

    /**
     * 上传图片
     */
    @PostMapping("/upload/image")
    public RestResult uploadImage(@RequestParam("file") MultipartFile file){
        return this.execute(ArticleUploadImageCmd.of(file));
    }

    @GetMapping("/image/{imageId}")
    public void getImage(@PathVariable String imageId, HttpServletResponse response) {
        this.execute(GetImageReq.of(imageId, response)).getData();
    }
}
