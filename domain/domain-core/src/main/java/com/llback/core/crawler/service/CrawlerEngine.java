package com.llback.core.crawler.service;

import com.llback.core.crawler.eo.CrawlerConfigEo;

/**
 * 爬虫引擎接口
 *
 * @author llback
 */
public interface CrawlerEngine {
    /**
     * 执行爬虫
     *
     * @param config 爬虫配置
     * @return 爬取结果数据
     */
    String execute(CrawlerConfigEo config);
}
