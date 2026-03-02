package com.llback.api.app.crawler.dto.req;

import com.llback.frame.dto.Query;
import lombok.Data;

/**
 * 查询最新爬取记录请求
 *
 * @author llback
 */
@Data
public class QueryLatestCrawlerRecordReq implements Query {
    /**
     * 配置ID（必填）
     */
    private String configId;
}
