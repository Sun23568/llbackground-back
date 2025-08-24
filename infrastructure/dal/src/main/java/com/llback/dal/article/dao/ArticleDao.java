package com.llback.dal.article.dao;

import com.llback.dal.article.po.ArticleContentPo;
import com.llback.dal.article.po.ArticlePo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 文章Dao
 */
@Mapper
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
    List<ArticlePo> listArticle(String userId, boolean queryAll);

    /**
     * 更新文章
     */
    int updateArticleBaseInfo(ArticlePo articlePo);

    /**
     * 根据文章id查询文章
     */
    ArticleContentPo getArticleContentById(String articleId, String draft);

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
    int addArticleContent(ArticleContentPo articleContentPo);

    /**
     * 删除文章
     *
     * @author yz.sun
     * @date 2025/3/28
     */
    int removeArticle(String articleId);

    /**
     * 删除文章草稿
     */
    void deleteDraft(String articleId, boolean justRemoveDraft);

    /**
     * 判断文章是否存在(非草稿)
     */
    int hasInst(String string);

    ArticlePo getArticleBaseInfo(String string);
}
