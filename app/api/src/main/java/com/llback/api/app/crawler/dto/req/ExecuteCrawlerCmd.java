package com.llback.api.app.crawler.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

import java.util.Map;

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

    /**
     * 执行变量，用于替换 URL/Body 中的 {{占位符}}
     * key: 变量名, value: 替换值
     */
    private Map<String, String> variables;
}
