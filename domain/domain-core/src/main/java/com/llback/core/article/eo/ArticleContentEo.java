package com.llback.core.article.eo;

import com.llback.common.types.Flag;
import com.llback.common.types.StringId;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 文章内容
 */
@Getter
@Builder
public class ArticleContentEo {
    /**
     * 唯一键
     */
    private StringId pkId;

    /**
     * 文章ID
     */
    private StringId articleId;

    /**
     * draft
     */
    private Flag draft;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
