package com.llback.api.app.crawler.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

/**
 * 更新爬虫配置命令
 *
 * @author llback
 */
@Data
public class UpdateCrawlerConfigCmd implements Command {
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
}
