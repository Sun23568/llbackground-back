package com.llback.core.crawler.repository;

import com.llback.common.types.StringId;
import com.llback.core.crawler.eo.CrawlerRecordEo;
import com.github.pagehelper.PageInfo;

/**
 * 爬取记录仓储接口
 *
 * @author llback
 */
public interface CrawlerRecordRepository {
    /**
     * 保存爬取记录
     *
     * @param crawlerRecordEo 爬取记录实体
     */
    void save(CrawlerRecordEo crawlerRecordEo);

    /**
     * 根据配置ID分页查询记录
     *
     * @param configId 配置ID
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageInfo<CrawlerRecordEo> findByConfigId(StringId configId, int pageNum, int pageSize);

    /**
     * 分页查询所有记录
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageInfo<CrawlerRecordEo> findAll(int pageNum, int pageSize);
}
