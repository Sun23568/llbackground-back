package com.heeexy.example.dto.resp;

import lombok.Data;

/**
 * @author yz.sun
 * @description: 文章信息
 * @date 2025/3/19
 */
@Data
public class ArticleInfo {
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
     * 是否草稿
     */
    private String craft;
}
