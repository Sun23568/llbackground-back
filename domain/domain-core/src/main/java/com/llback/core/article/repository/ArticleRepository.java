package com.llback.core.article.repository;

import com.github.pagehelper.PageInfo;
import com.llback.common.types.StringId;
import com.llback.common.types.UserId;
import com.llback.core.article.eo.ArticleContentEo;
import com.llback.core.article.eo.ArticleEo;

/**
 * 文章仓储
 *
 * @author yz.sun
 * @date 2025/8/22
 */
public interface ArticleRepository {
    /**
     * 获取文章列表
     *
     * @author yz.sun
     * @date 2025/8/22
     */
    PageInfo<ArticleEo> listArticle(int pageIndex, int pageSize, UserId userId, boolean queryAll);

    /**
     * 添加文章
     */
    int addArticle(ArticleEo eo);

    /**
     * 修改文章
     */
    int updateArticle(ArticleEo eo);

    /**
     * 删除文章草稿
     */
    void removeContent(StringId articleId, boolean justRemoveDraft);

    /**
     * 是否存在非草稿的存储
     */
    boolean hasInst(StringId articleId);

    /**
     * 添加文章内容
     */
    int addContent(ArticleContentEo articleContentEo);

    /**
     * 获取文章基本信息
     */
    ArticleEo getArticleBaseInfo(StringId articleId);

    /**
     * 删除文章
     */
    int removeArticle(String articleId);
}
