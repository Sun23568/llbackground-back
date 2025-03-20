package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.ArticleDao;
import com.heeexy.example.dto.ArticleReq;
import com.heeexy.example.dto.resp.ArticleInfo;
import com.heeexy.example.util.AssertUtils;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

/**
 * @author: heeexy
 * @date: 2017/10/24 16:07
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    /**
     * 文章列表
     */
    public JSONObject listArticle(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = articleDao.countArticle(jsonObject);
        List<JSONObject> list = articleDao.listArticle(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 更新文章
     */
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateArticle(ArticleReq articleReq) {
        AssertUtils.assertNotEmpty(articleReq.getContent(), "文章内容");
        AssertUtils.assertNotEmpty(articleReq.getTitle(), "文章标题");
        AssertUtils.assertNotEmpty(articleReq.getCraft(), "是否草稿");

        if (StringUtils.isEmpty(articleReq.getArticleId())) {
            // 新增文章
            articleReq.setArticleId(UUIDUtils.getUUID());
            articleReq.setContentId(UUIDUtils.getUUID());
            articleDao.addArticle(articleReq);
            articleDao.addArticleContent(articleReq);
        } else {
            // 更新标题
            articleDao.updateArticleBaseInfo(articleReq);
            // 更新内容
            articleDao.updateArticleContent(articleReq);
        }
        return CommonUtil.successJson();
    }

    /**
     * 根据文章id查询文章
     *
     * @param articleId
     * @param craft
     * @return
     */
    public JSONObject getArticleById(String articleId, String craft) {
        ArticleInfo articleById = articleDao.getArticleById(articleId, craft);
        return CommonUtil.successJson(articleById);
    }
}
