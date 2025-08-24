package com.llback.dal.article.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章内容Po
 *
 * @author yz.sun
 * @date 2025/8/22
 */
@Data
public class ArticleContentPo {
    /**
     * 主键
     */
    private String pkId;

    /**
     * 文章id
     */
    private String articleId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 草稿
     */
    private String draft;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
