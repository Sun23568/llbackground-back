package com.llback.api.app.crawler.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 爬取记录DTO
 *
 * @author llback
 */
@Data
public class CrawlerRecordDto {
    /**
     * 主键ID
     */
    private String pkId;

    /**
     * 配置ID
     */
    private String configId;

    /**
     * 配置名称
     */
    private String configName;

    /**
     * 执行时间
     */
    private LocalDateTime executeTime;

    /**
     * 执行状态
     */
    private String status;

    /**
     * 爬取结果数据
     */
    private String resultData;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 执行耗时(毫秒)
     */
    private Long duration;
}
