package com.heeexy.example.dto;

import lombok.Data;

/**
 * 文章请求参数
 *
 * @author yz.sun
 * @date 2025/3/13
 */
@Data
public class ArticleReq {
    /**
     * 文章id
     */
    private String articleId;

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
}
