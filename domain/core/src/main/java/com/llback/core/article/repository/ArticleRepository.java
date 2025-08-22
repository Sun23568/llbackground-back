package com.llback.core.article.repository;

import com.github.pagehelper.PageInfo;
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
    PageInfo<ArticleEo> listArticle(int pageIndex, int pageSize);
}
