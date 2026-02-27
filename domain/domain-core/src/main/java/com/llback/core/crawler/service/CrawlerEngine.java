package com.llback.core.crawler.service;

import com.llback.core.crawler.eo.CrawlerConfigEo;

import java.util.Collections;
import java.util.Map;

/**
 * 爬虫引擎接口
 *
 * @author llback
 */
public interface CrawlerEngine {
    /**
     * 执行爬虫（不带变量，供定时调度使用）
     *
     * @param config 爬虫配置
     * @return 爬取结果数据
     */
    default String execute(CrawlerConfigEo config) {
        return execute(config, Collections.emptyMap());
    }

    /**
     * 执行爬虫（带变量，用于替换 URL/Body 中的 {{占位符}}）
     *
     * @param config    爬虫配置
     * @param variables 占位符变量 Map
     * @return 爬取结果数据
     */
    String execute(CrawlerConfigEo config, Map<String, String> variables);
}
