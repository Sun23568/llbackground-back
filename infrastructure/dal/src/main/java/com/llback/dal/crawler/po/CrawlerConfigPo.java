package com.llback.dal.crawler.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 爬虫配置持久化对象
 *
 * @author llback
 */
@Data
public class CrawlerConfigPo {
    /**
     * 主键ID
     */
    private String pkId;
    /**
     * 配置名称
     */
    private String configName;

    /**
     * 目标URL
     */
    private String targetUrl;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求头(JSON格式)
     */
    private String requestHeaders;

    /**
     * 请求体
     */
    private String requestBody;

    /**
     * 前置处理器配置(JSON格式)
     */
    private String preProcessor;

    /**
     * 后置处理器配置(JSON格式)
     */
    private String postProcessor;

    /**
     * Cron表达式
     */
    private String cronExpression;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
