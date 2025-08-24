package com.llback.api.app.article.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

/**
 * 删除文章命令
 */
@Data
public class RemoveArticleCmd implements Command {
    /**
     * 文章ID
     */
    private String articleId;
}
