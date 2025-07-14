package com.llback.dal.article.po;

import lombok.Data;

/**
 * 文章Po
 */
@Data
public class ArticlePo {

    /**
     * 文章id
     */
    private String articleId;

    /**
     * 文章内容id
     */
    private String contentId;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 是否草稿
     */
    private String craft;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;
}
