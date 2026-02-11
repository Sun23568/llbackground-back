package com.llback.api.app.crawler.dto.req;

import com.llback.frame.dto.Query;
import lombok.Data;

/**
 * 查询爬取记录列表请求
 *
 * @author llback
 */
@Data
public class QueryCrawlerRecordListReq implements Query {
    /**
     * 配置ID（可选）
     */
    private String configId;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    private Integer pageRow = 10;
}
