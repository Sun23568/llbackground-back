package com.llback.api.app.article.dto.resp;

import lombok.Data;

/**
 * 文章内容返回
 *
 * @author yz.sun
 * @date 2025/8/22
 */
@Data
public class ArticleContentResp {
    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;
}
