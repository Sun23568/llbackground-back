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
    @RequiresPermissions("article:list")
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
     * 上传文件
     *
     * @author yz.sun
     * @date 2025/3/25
     */
    @PostMapping("/uploadFile")
    public JSONObject uploadFile(@RequestParam("file") MultipartFile file) {
        if (file == null){
            return null;
        }
        return articleService.uploadFile(file);
    }

    /**
     * 获取图片
     */
    @GetMapping("/getImg")
    public byte[] getImgById(@RequestParam String fileId) {
        try {
            URL url = new URL("https://pics1.baidu.com/feed/48540923dd54564e3d64f17f8d7f0a8dd0584fa7.jpeg@f_auto?token=698e0d8fc993fac4c2dd62feebcf1465");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                byte[] imageBytes = outputStream.toByteArray();
                inputStream.close();
                outputStream.close();

                return imageBytes;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
