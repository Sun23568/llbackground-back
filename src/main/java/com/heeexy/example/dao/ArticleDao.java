package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dto.ArticleReq;
import com.heeexy.example.dto.resp.ArticleInfo;

import java.util.List;

/**
 * @author: heeexy
 * @description: 文章Dao层
 * @date: 2017/10/24 16:06
 */
public interface ArticleDao {
    /**
     * 新增文章
     */
    int addArticle(JSONObject jsonObject);

    /**
     * 统计文章总数
     */
    int countArticle(JSONObject jsonObject);

    /**
     * 文章列表
     */
    List<JSONObject> listArticle(JSONObject jsonObject);

    /**
     * 更新文章
     */
    int updateArticleBaseInfo(ArticleReq articleReq);

    /**
     * 根据文章id查询文章
     */
    ArticleInfo getArticleById(String articleId, String craft);

    void updateArticleContent(ArticleReq articleReq);
}
