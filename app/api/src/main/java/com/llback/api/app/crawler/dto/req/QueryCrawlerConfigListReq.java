package com.llback.api.app.crawler.dto.req;

import com.llback.frame.dto.Query;
import lombok.Data;

/**
 * 查询爬虫配置列表请求
 *
 * @author llback
 */
@Data
public class QueryCrawlerConfigListReq implements Query {
    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    private Integer pageRow = 10;
}
