package com.llback.api.app.crawler.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

/**
 * 删除爬虫配置命令
 *
 * @author llback
 */
@Data
public class DeleteCrawlerConfigCmd implements Command {
    /**
     * 主键ID
     */
    private String pkId;
}
