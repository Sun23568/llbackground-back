package com.llback.api.app.article.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

/**
 * 更新或新增文章
 */
@Data
public class UpdateArticleCmd implements Command {
    /**
     * 文章ID
     */
    private String articleId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 草稿
     */
    private String draft;

    /**
     * 公开
     */
    private String publicFlag;
}
