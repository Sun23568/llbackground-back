package com.llback.dal.article.dao;

import com.llback.dal.article.po.ArticlePo;

import java.util.List;

/**
 * 文章Dao
 */
public interface ArticleDao {
    /**
     * 新增文章
     */
    int addArticle(ArticlePo articleReq);

    /**
     * 统计文章总数
     */
    int countArticle(ArticlePo articlePo);

    /**
     * 文章列表
     */
    List<ArticlePo> listArticle(ArticlePo articlePo);

    /**
     * 更新文章
     */
    int updateArticleBaseInfo(ArticlePo articlePo);

    /**
     * 根据文章id查询文章
     */
    ArticlePo getArticleById(String articleId, String craft);

    /**
     * 更新文章内容
     *
     * @param articlePo
     */
    void updateArticleContent(ArticlePo articlePo);

    /**
     * 新增文章内容
     *
     * @author yz.sun
     * @date 2025/3/20
     */
    int addArticleContent(ArticlePo articlePo);

    /**
     * 删除文章
     *
     * @author yz.sun
     * @date 2025/3/28
     */
    void removeArticle(String articleId);
}
