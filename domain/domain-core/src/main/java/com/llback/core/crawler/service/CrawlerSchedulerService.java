package com.llback.core.crawler.service;

import com.llback.core.crawler.eo.CrawlerConfigEo;

/**
 * 爬虫调度服务接口
 *
 * @author llback
 */
public interface CrawlerSchedulerService {
    /**
     * 调度任务
     *
     * @param config 爬虫配置
     */
    void schedule(CrawlerConfigEo config);

    /**
     * 取消调度
     *
     * @param configId 配置ID
     */
    void unschedule(String configId);

    /**
     * 重新调度
     *
     * @param config 爬虫配置
     */
    void reschedule(CrawlerConfigEo config);
}
