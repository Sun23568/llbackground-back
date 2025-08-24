package com.llback.api.app.article.fetch;

import com.llback.api.app.article.dto.resp.ArticleContentResp;
import com.llback.common.types.StringId;

/**
 * 文章查询接口
 *
 * @author yz.sun
 * @date 2025/8/22
 */
public interface ArticleFetch {
    /**
     * 查询文章内容
     *
     * @author yz.sun
     * @date 2025/8/22
     */
    ArticleContentResp queryArticleContent(StringId articleId, String draft);
}
