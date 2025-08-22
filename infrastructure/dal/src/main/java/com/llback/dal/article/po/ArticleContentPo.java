package com.llback.dal.article.po;

import lombok.Data;

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
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;
}
