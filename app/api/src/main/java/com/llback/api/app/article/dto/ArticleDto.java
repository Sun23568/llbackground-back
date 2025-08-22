package com.llback.api.app.article.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章DTO
 *
 * @author yz.sun
 * @date 2025/8/22
 */
@Data
public class ArticleDto {
    /**
     * 文章ID
     */
    private String pkId;

    /**
     * 标题
     */
    private String title;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
