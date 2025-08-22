package com.llback.core.article.eo;

import com.llback.common.types.ArticleTitle;
import com.llback.common.types.StringId;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 文章EO
 *
 * @author yz.sun
 * @date 2025/8/22
 */
@Builder
public class ArticleEo {
    /**
     * 主键
     */
    private StringId pkId;

    /**
     * 标题
     */
    private ArticleTitle title;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
