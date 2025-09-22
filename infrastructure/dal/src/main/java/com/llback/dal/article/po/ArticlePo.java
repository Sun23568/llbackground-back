package com.llback.dal.article.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章Po
 */
@Data
public class ArticlePo {
    /**
     * 主键id
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
     * 公开标识
     */
    private String publicFlag;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
