package com.llback.core.crawler.eo;

import com.llback.common.types.StringId;
import com.llback.core.crawler.enums.CrawlerStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 爬取记录实体对象
 *
 * @author llback
 */
@Getter
@Builder
public class CrawlerRecordEo {
    /**
     * 主键ID
     */
    private StringId pkId;

    /**
     * 配置ID
     */
    private StringId configId;

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
    private CrawlerStatus status;

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
