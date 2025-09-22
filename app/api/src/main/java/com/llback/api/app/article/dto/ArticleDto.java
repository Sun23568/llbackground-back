package com.llback.api.app.article.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 文章DTO
 *
 * @author yz.sun
 * @date 2025/8/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
     * 作者
     */
    private String author;

    /**
     * 作者姓名
     */
    private String authorName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
