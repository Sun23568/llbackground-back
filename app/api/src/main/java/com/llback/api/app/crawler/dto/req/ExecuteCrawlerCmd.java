package com.llback.api.app.crawler.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

/**
 * 执行爬虫命令
 *
 * @author llback
 */
@Data
public class ExecuteCrawlerCmd implements Command {
    /**
     * 配置ID
     */
    private String configId;
}
