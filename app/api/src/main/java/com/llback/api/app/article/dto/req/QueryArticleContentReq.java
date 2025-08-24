package com.llback.api.app.article.dto.req;

import com.llback.frame.dto.Query;
import lombok.Data;

/**
 * 查询文章内容请求
 *
 * @author yz.sun
 * @date 2025/8/22
 */
@Data
public class QueryArticleContentReq implements Query {
    /**
     * 文章ID
     */
    private String articleId;

    /**
     * 是否草稿
     */
    private String draft;
}
