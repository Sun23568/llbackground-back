package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.config.annotation.RequiresPermissions;
import com.heeexy.example.dto.ArticleReq;
import com.heeexy.example.dto.resp.ArticleInfo;
import com.heeexy.example.service.ArticleService;
import com.heeexy.example.util.AssertUtils;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author: heeexy
 * @description: 文章相关Controller
 * @date: 2017/10/24 16:04
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 查询文章列表
     */
    @GetMapping("/listArticle")
    public JSONObject listArticle(HttpServletRequest request) {
        return articleService.listArticle(CommonUtil.request2Json(request));
    }

    /**
     * 修改文章
     */
    @RequiresPermissions("article:update")
    @PostMapping("/updateArticle")
    public JSONObject updateArticle(@RequestBody ArticleReq articleReq) {
        return articleService.updateArticle(articleReq);
    }

    /**
     * 根据文章ID获取文章信息
     */
    @RequiresPermissions("article:update")
    @GetMapping("/getArticleById")
    public JSONObject getArticleById(@RequestParam String articleId, @RequestParam String craft) {
        AssertUtils.assertNotEmpty(articleId, "文章ID");
        return articleService.getArticleById(articleId, craft);
    }

    /**
     * 删除文章
     *
     * @author yz.sun
     * @date 2025/3/28
     */
    @PostMapping("/deleteArticle")
    public JSONObject deleteArticle(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "articleId");
        return articleService.deleteArticle(requestJson.getString("articleId"));
    }

    /**
     * 上传文件
     *
     * @author yz.sun
     * @date 2025/3/25
     */
    @PostMapping("/uploadFile")
    public JSONObject uploadFile(@RequestParam("file") MultipartFile file) {
        if (file == null) {
            return null;
        }
        return articleService.uploadFile(file);
    }

    /**
     * 获取图片
     */
    @GetMapping("/getImg")
    public byte[] getImgById(@RequestParam String fileId) {
        return articleService.getImgById(fileId);
    }
}
